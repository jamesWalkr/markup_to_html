package com.example.service;

import com.example.exceptions.StorageException;
import com.example.models.Text;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class GrammarCheckerService {

    private final Logger logger = LoggerFactory.getLogger(GrammarCheckerService.class);

    public void checkText(String text) throws IOException {
        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-Us"));
        List<RuleMatch> matchList = langTool.check(text);
        for (RuleMatch match : matchList){
            logger.debug("Potential error at characters: {} - {}: {}",  match.getFromPos(), match.getToPos() ,match.getMessage());
            logger.debug("Suggested corrections: {}, number of suggestions: {} ", match.getSuggestedReplacements(), match.getSuggestedReplacements().size());
            String correctedWord = match.getSuggestedReplacements().get(0);

        }
    }

    public void readFile(Path filePath){
        try{
            List<String> lines = Files.readAllLines(filePath);
            for (String line: lines){
                // logger.debug(line);
                checkText(line);
            }
        }catch (IOException e){
            throw new StorageException("Could not read file");
        }

    }




}
