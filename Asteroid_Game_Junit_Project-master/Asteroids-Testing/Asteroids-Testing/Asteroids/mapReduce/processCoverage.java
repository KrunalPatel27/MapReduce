/**
 * Created by krupa on 10/3/2017.
 */
import java.util.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;


public class processCoverage {
    

    //Mapper class
    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable ,   /* Input key Type */
                    Text,                /*Input value Type*/
                    Text,                /*Output key Type*/
                    Text>        /*Output value Type*/
    {
        private int linesOfCoverageByTest = 0;

        public void configure(JobConf conf){
            try{
               // Path pt=new Path("hdfs:/path/to/file");//Location of file in HDFS
               // FileSystem fs = FileSystem.get(conf);
                String filename = conf.get("map.input.file");
                BufferedReader br=new BufferedReader(new FileReader(filename)); //.open(pt)));
                while (br.readLine() != null) linesOfCoverageByTest++;
                br.close();                     
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        //Map function
        public void map(LongWritable key, Text value,
                        OutputCollector<Text,Text> output,
                        Reporter reporter) throws IOException
        {
            // get filename
            FileSplit fsplit = (FileSplit) reporter.getInputSplit();
            String testName = fsplit.getPath().getName();

            String line = value.toString();


            output.collect(new Text(line), new Text(testName + "\t"+ linesOfCoverageByTest));
        }
    }


    //Reducer class
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, Text, Text, Text >
    {   
        private TreeMap<String,String> treeSet;

        public String sortedByCoverage (Iterator <Text> values){
            treeSet = new TreeMap<String,String>();
            String lasttoken = null;
            String line = "";
            int length =0;
            while (values.hasNext()) {
                line =  ((Text)values.next()).toString();
                StringTokenizer s = new StringTokenizer(line,"\t"); 
                String testName = s.nextToken(); 
                String linesOfCoverageByTest = s.nextToken(); //Integer.parseInt(s.nextToken());
                treeSet.put(new String(linesOfCoverageByTest), new String(testName));
                length++;
            }
             // Get a set of the entries
            Set set = treeSet.entrySet();
            // Get an iterator
            Iterator i = set.iterator();

            String[] array = new String[length];
            String previousKey= "";
            int counter = length - 1;
            
            while(i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                //to deal with descending order
                array[counter--] = me.getValue().toString();
                if(previousKey == me.getKey() ){
                    if(array[counter+1].compareTo( array[counter+2]) > 0 ){
                        String temp = array[counter +1];
                        array[counter+1] = array[counter+2];
                        array[counter+2] = temp;
                    }
                }
                previousKey = me.getKey().toString();
            }
            treeSet.clear();
            String returnString = array[0];
            for(int x =1; x < length; x++){
                returnString = returnString + ", " + array[x];
            }
            return returnString;
            
        }

        //Reduce function
        public void reduce( Text key, Iterator <Text> values,
                            OutputCollector<Text, Text> output, Reporter reporter) throws IOException
        {
           String returnVal = sortedByCoverage(values);

            output.collect(key, new Text(returnVal) );
            


        }
    }


    //Main function
    public static void main(String args[])throws Exception {
        JobConf conf = new JobConf(processCoverage.class);

        conf.setJobName("TestCoverage");
        conf.setJarByClass(processCoverage.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(Text.class);

        conf.setMapperClass(E_EMapper.class);
        //conf.setCombinerClass(E_EReduce.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
       // conf.waitForCompletion(true);
    }
}