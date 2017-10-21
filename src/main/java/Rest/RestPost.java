package Rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestPost {
    public List<String> ExtractFeaturesRest(String content) throws IOException
	{

        String data1 = URLEncoder.encode("ent", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
        String data2 = URLEncoder.encode("sentence", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
        List<String> urls = new ArrayList<String>();

        urls.add("http://d2dcrc.cse.unsw.edu.au:9091/ExtractionAPI-0.0.1-SNAPSHOT/rest/entity/continent");
        urls.add("http://d2dcrc.cse.unsw.edu.au:9091/ExtractionAPI-0.0.1-SNAPSHOT/rest/entity/person");
        urls.add("http://d2dcrc.cse.unsw.edu.au:9091/ExtractionAPI-0.0.1-SNAPSHOT/rest/entity/organization");

        String output;
        String Val = "";
        List<String> results = new ArrayList<String>();

        for(String url: urls) {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data1);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            System.out.println("Output from Server .... \n");
            while ((output = rd.readLine()) != null) {
                String[] out = output.split(":");
                out[1] = out[1].replaceAll("\\s*\"", "");
                out[1] = out[1].replaceAll("}", "");
                out[1] = out[1].replaceAll(" ", "");
                String[] keywords = out[1].split(",");
                for(String K : keywords) {
                    if (K != "") {
                        results.add(K);
                        Val += K + ", ";
                        results.add(K);
                    }
                }

//                Val += out[1] + ", ";
//                results.add(out[1]);
            }

            wr.close();
            rd.close();
        }

        URL u = new URL("http://d2dcrc.cse.unsw.edu.au:9091/ExtractionAPI-0.0.1-SNAPSHOT/rest/keyword");
        URLConnection conn = u.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data2);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((output = rd.readLine()) != null) {
            String[] out = output.split("\",\"");
            out[1] = out[1].replaceAll("keyword\":\"", "");
            String[] keywords = out[1].split(",");
            for(String K : keywords) {
                if (K != "") {
                    results.add(K);
                    Val += K + ", ";

                }
            }
        }
        wr.close();
        rd.close();

        List<String> bullyWords = new ArrayList<String>(Arrays.asList("Abuse","Abusive","Acknowledge","Actingout","Actual","Addictive",
                "Administration","Adult","Advocate","Affect","Afraid","Aggravate","Aggressive","Alone","Ambush","Annoy",
                "Anonymous","Antagonistic","Antagonize","Anxiety","Apathetic","Appropriate","Arrogant","Assault","Attack",
                "Attentive","Attitude","Audience","Authority", "Beatup","Behavior","Belittle","Bias","Blase","Blindside",
                "Boorish","Bother","Braggart","Bully","Burden","Bystander", "Callow","Campus","Capitulate","Captious",
                "Case","Cautious","Challenge","Charges","Cheat","Childhood","Churlish","Coaches","Cold","Communication",
                "Complain","Complaint","Compulsive","Concern","Confidence","Conflict","Consequence","Console","Consult",
                "Contemptible","Control","Corner","Counselor","Courage","Covetous","Creepy","Crime","Criminal","Critical",
                "Crude","Cruel","Crying","Culpable","Curt","Cyber-bullying","Cynical","Decency","Deed","Demeaning","Dependent",
                "Depression","Desensitize","Despair","Despot","Destructive","Devastate","Devious","Dictatorial","Die",
                "Different","Disaster","Discourage","Discussion","Disdainful","Dishonesty","Dishonorable","Disregard",
                "Disrespectful","Dodge","Dominate","Effect","Effort","Egoist","Egotism","Elitist","Elude","Embarrassment",
                "Emotional","Empathy","Endure","Epithet","Escalation","Evade","Evil","Exclusion","Explosive","Exposure",
                "Expulsion","Extort","Extravagant","Extreme","Extreme","Failure","Falseness","Fanatic","Favoritism","Fear",
                "Fervid","Flashback","Focus","Football","Force","Frailty","Friendship","Fright","Frightened","Frozen",
                "Gang","Glib","Gossip","Grouchy","Guilty","Harassment","Hard-hearted","Haunted","Hazing","Head-off","Healing",
                "Heedless","Help","Hidden","Hide","Hopeless","Hostile","Hounded","Hurt","Ignoble","Ignorant","Ignore",
                "Ill-tempered","Impetuous","Implacable","Impolite","Improper","Imprudent","Impudent","Impunity",
                "Inappropriate","Incidence","Indecent","Indecorous","Indifference","Ineffective","Innocent","Insight",
                "Insolent","Insulting","Intentional","Intolerant","Irascible","Irresponsible","Isolated","Jaundiced","Jealousy",
                "Judge","Jumpy","Justice","Justify","Juvenile","Keen","Kicked","Kindness","Knockdown","Knowledge","Knuckle",
                "Language","Lethal","Lifelong","Litigation","Loathsome","Loss","Malevolent","Malicious","Manipulative",
                "Marked","Mean","Meddler","Medical","Memories","Merciless","Mercurial","Mercy","Misbehavior","Miserable",
                "Misery","Mistrustful","Momentum","Monitor","Nefarious","Neglectful","Negligent","Nervous","Neurotic",
                "Notorious","Nuisance","Obnoxious","Obsequious","Odious","Offensive","Oppressive","Ostracize","Out-of-line",
                "Outcast","Outrageous","Overwhelm","Pain","Painful","Partial","Passive","Peace","Peers","Pervasive","Petulant",
                "Physical","Pickedon","Pity","Police","Popularity","Prejudicial","Preposterous","Pretentious","Prevention",
                "Preyon","Problem","Proceed","Protection","Protest","Psychological","Punched","Punishment","Pushing","Put-down",
                "Quake","Quandary","Quarrelsome","Querulous","Quibbler","Quirky","Quiver","Rancorous","Reckless","Report",
                "Representation","Repugnance","Reticent","Rights","Rude","Ruthless",
                "School","School","Scornful","Secrets","Seek","Selfish","Sensitive","Seriousness","Shake","Shame","Shock",
                "Shouting","Shoving","Shunned","Silence","Skill","Slur","Socialize","Solution","Sour","Spiton","Sports",
                "Stern","Struggle","Suffering","Suicidal","Sullen","Support","Surly","Survivor","Suspect","Suspension",
                "Suspicious","Target","Taunt","Tears","Tease","Temper","Terrify","Testy","Thoughtless","Thug","Thuggish",
                "Tolerance","Tolerate","Torment","Tormentor","Trauma","Treacherous","Treatment","Trick","Trust","Uncivil",
                "Uncouth","Unethical","Unfair","Unify","Unmannerly","Unreasonable","Unrefined","Unrelenting","Unsavory",
                "Unworthy","Verbalabuse","Vicious","Victim","Vigilance","Vile","Villainous","Violence","Violent","Volatile",
                "Warning","Wary","Waspish","Watchful","Weakness","Weary","Welfare","Whine","Why","Wicked","Wishy-washy",
                "Words","Worry","Wound","Wrath","Wrong","Yelling","Youngsters","Youth","Zealot"));

        List<String> common = new ArrayList<String>(bullyWords);
        common.retainAll(results);
        System.out.println(results.toString());

        return common;
	}}
