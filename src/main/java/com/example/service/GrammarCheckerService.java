package com.example.service;

import com.example.exceptions.StorageException;
import com.example.models.Text;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrammarCheckerService {

    private final Logger logger = LoggerFactory.getLogger(GrammarCheckerService.class);


    // take a list of lines
    // store eac line as string buffer object to be updated with correct letters
    // return a string of corrected lines
    public String checkText(List<String> textList) throws IOException {
        if (!textList.isEmpty()){
            logger.debug("Size is --> {}", textList.size());
        }
        StringBuffer sb = new StringBuffer();
        for (String s : textList){
            sb.append(s);
            sb.append("\n");
            // logger.debug(String.valueOf(sb));
        }
        // logger.debug(sb.toString());
        return checkGrammar(sb.toString(), sb);
    }

    // take string if each line and string buffer
    // check each line for errors hen update string buffer of line with corrects letters
    public String checkGrammar(String text, StringBuffer stringBuffer){
        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-Us"));
        int offset = 0;

        List<RuleMatch> matchList = null;
        try {
            matchList = langTool.check(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (RuleMatch match : matchList){
            stringBuffer.replace(match.getFromPos() - offset, match.getToPos() -offset, match.getSuggestedReplacements().get(0));
            offset += (match.getToPos() - match.getFromPos() - match.getSuggestedReplacements().get(0).length());
        }
         // logger.debug("return: {}",List.of(stringBuffer.toString()));
        return stringBuffer.toString();
    }



    // return a list of lines that are in the file
    public List<String> readFile(Path filePath){
        List<String> allLines = new ArrayList<>();
        try{
            List<String> lines = Files.readAllLines(filePath);
            // logger.debug(lines.toString());
            allLines.addAll(lines);
        }catch (IOException e){
            throw new StorageException("Could not read file");
        }
        return allLines;
    }


    // write the list of corrected lines to the file.
    public void writeFile(String line){
        try {
            Path fielPath = Paths.get("/home/james/file_storage/test_3.md");
            List<String> dataToWrite = List.of(line);
            Files.write(fielPath, dataToWrite, StandardOpenOption.CREATE);
        }catch (IOException e){
            throw new StorageException("Error writing to file");
            // e.printStackTrace();
        }
    }
}
