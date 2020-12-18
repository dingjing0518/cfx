package cn.cf.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SynonymAnalyzerUtil {

	 /**
    *
    * 此方法描述的是：进行中文拆分
    */
   public static String analyzeChinese(String input, boolean userSmart) throws IOException {
       StringBuffer sb = new StringBuffer();
       StringReader reader = new StringReader(input.trim());
       // true　用智能分词　，false细粒度
       IKSegmenter ikSeg = new IKSegmenter(reader, userSmart);
       for (Lexeme lexeme = ikSeg.next(); lexeme != null; lexeme = ikSeg.next()) {
           if (lexeme.getLexemeText().length()>1) {
               sb.append(lexeme.getLexemeText()).append(" ");
           }
       }
       return sb.toString();
   }
    /**
     *
     * 此方法描述的是：每隔2个字符加一个空格
     */
    public static String analyzeChinesefour(String input) throws IOException {
               String regex = "(.{3})";
      input = input.replaceAll (regex, "$1 ");
      return  input;
    }
   /**
    *
    * 此方法描述的是：针对上面方法拆分后的词组进行同义词匹配，返回TokenStream
    * synonyms.txt：同义词表，在resources目录下
    */
   public static TokenStream convertSynonymNew(String input) throws IOException{

       Version ver = Version.LUCENE_44;
       Map<String, String> filterArgs = new HashMap<String, String>();

       filterArgs.put("luceneMatchVersion", ver.toString());
       filterArgs.put("synonyms", "synonyms.txt");
       filterArgs.put("expand", "true");
       SynonymFilterFactory factory = new SynonymFilterFactory(filterArgs);
       factory.inform(new FilesystemResourceLoader());
       Analyzer IKAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_36);//按照空格分隔,中文不起作用
       TokenStream ts = factory.create(IKAnalyzer.tokenStream("someField", input));
       return ts;
   }

    public static TokenStream convertSynonym(String input) throws IOException{

        Version ver = Version.LUCENE_44;
        Map<String, String> filterArgs = new HashMap<String, String>();

        filterArgs.put("luceneMatchVersion", ver.toString());
        filterArgs.put("synonyms", "synonyms.txt");
        filterArgs.put("expand", "true");
        SynonymFilterFactory factory = new SynonymFilterFactory(filterArgs);
        factory.inform(new FilesystemResourceLoader());
         Analyzer IKAnalyzer = new IKAnalyzer();
        TokenStream ts = factory.create(IKAnalyzer.tokenStream("someField", input));
        return ts;
    }
   /**
    *
    * 此方法描述的是：将tokenstream拼成一个特地格式的字符串，交给IndexSearcher来处理
    */
   public static String displayTokens(TokenStream ts) throws IOException
   {
       StringBuffer sb = new StringBuffer();
       CharTermAttribute termAttr = ts.addAttribute(CharTermAttribute.class);
       ts.reset();
       while (ts.incrementToken())
       {
           String token = termAttr.toString();
           sb.append(token).append(" ");
       }
       ts.end();
       ts.close();
       return sb.toString();
   }




}
