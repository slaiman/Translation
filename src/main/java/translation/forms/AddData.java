package translation.forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import javax.swing.JLabel;
import javax.swing.JEditorPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class AddData extends JFrame {

	private JPanel contentPane;
	private JTextField txtNothing;
	private JTextField txtNothing_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddData frame = new AddData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddData() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 959, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblData = new JLabel("Data :");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblData.setBounds(45, 70, 46, 14);
		contentPane.add(lblData);

		final JEditorPane editorPane = new JEditorPane();
		editorPane.setText("\u0644\u0644\u0639\u0645\u0644 \u0645\u0646\u0627\u0641\u0639 \u0639\u062F\u0651\u0629 \u0648\u0647\u0648 \u0644\u064A\u0633 \u0645\u062C\u0631\u0651\u062F \u0627\u0644\u0642\u062F\u0631\u0629 \u0639\u0644\u0649 \u062A\u0623\u0645\u064A\u0646 \u062F\u062E\u0644 \u062B\u0627\u0628\u062A.");
		editorPane.setBounds(138, 11, 652, 137);
		contentPane.add(editorPane);

		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//db url
					String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
					Connection con = DriverManager.getConnection(url);

					//prepare properties for nlp
					Properties props = new Properties();
					props.put("annotators", "tokenize, ssplit, pos, parse");
					props.put("tokenize.language", "ar");
					props.put("segment.model",
							"edu/stanford/nlp/models/segmenter/arabic/arabic-segmenter-atb+bn+arztrain.ser.gz");
					props.put("ssplit.boundaryTokenRegex", "[.]|[!?]+|[!\\u061F]+");
					props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/arabic/arabic.tagger");

					StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

					Annotation annotation = pipeline.process(editorPane.getText());

					List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
					//loop over sentences
					for (CoreMap sentence : sentences) {

						//get the words for each sentence
						for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

							//get the word with its relative pos
                            String word = token.originalText();
							String pos = token.get(PartOfSpeechAnnotation.class);
							
							String query = "insert into [dbo].[Data] values('" + word + "','"
									+ txtNothing.getText() + "','" + pos + "')";

							//save to db
							Statement st = con.createStatement();
							st.execute(query);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(138, 339, 89, 23);
		contentPane.add(btnAdd);

		JLabel lblType = new JLabel("Type :");
		lblType.setBounds(45, 221, 46, 14);
		contentPane.add(lblType);

		txtNothing = new JTextField();
		txtNothing.setText("Nothing");
		txtNothing.setBounds(138, 218, 134, 20);
		contentPane.add(txtNothing);
		txtNothing.setColumns(10);

		JLabel lblTranslation = new JLabel("Translation :");
		lblTranslation.setBounds(45, 276, 89, 14);
		contentPane.add(lblTranslation);

		txtNothing_1 = new JTextField();
		txtNothing_1.setText("NoThing");
		txtNothing_1.setBounds(138, 273, 134, 20);
		contentPane.add(txtNothing_1);
		txtNothing_1.setColumns(10);
	}
}
