package com.example.lab3.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String response;


    /*<p\sclass="mtZOt">.*</p>*/ //todo Верхушка
    /*<p class="mtZOt">.* <a*/ //todo Первое описание
    /*href="/aries/description/element/">.*</a>*/ //todo Второе описание
    /*href="\/aries\/description\/element\/">(.*\n)[^<]**/ //todo Третье описание
    public Parser(String response){
        this.response = response;
    }
    public ArrayList<String> getLinks(){
        ArrayList<String> matched = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\/\\w+\\/description\\/");
        Matcher matcher = pattern.matcher(response);
        while(matcher.find()){
            matched.add(matcher.group());
        }
        StringBuilder builder = new StringBuilder();
        Set<String>set = new HashSet<>(matched);
        matched.clear();
        matched.addAll(set);
        for(String link: matched){
            builder.append("https://horoscopes.rambler.ru");
            builder.append(link);
            links.add(builder.toString());
            builder.delete(0, builder.capacity());
        }
        return links;
    }
    private ArrayList<String> description_parts;
    public String getZodiacDescription(){
        Pattern pattern;
        Matcher matcher;
        description_parts = new ArrayList<>();
        pattern = Pattern.compile("<p class=\"mtZOt\">[^<\\/p>]*");
        matcher = pattern.matcher(response);
        if(matcher.find()){
            description_parts.add(matcher.group());
        }
        pattern = Pattern.compile("<p class=\"mtZOt\">.* <a");
        matcher = pattern.matcher(response);
        if(matcher.find()){
            description_parts.add(matcher.group());
        }
        pattern = Pattern.compile("href=\"\\/\\w+\\/description\\/element\\/\">[^<\\/a]*");
        matcher = pattern.matcher(response);
        if(matcher.find()){
            description_parts.add(matcher.group());
        }
        pattern = Pattern.compile("href=\"\\/\\w+\\/description\\/element\\/\">[^p]*");
        matcher = pattern.matcher(response);
        if(matcher.find()){
            description_parts.add(matcher.group());
        }
        return getDescription(description_parts);
    }

    /*[^(<pclass="mtZOt">\/)]+*/ //todo 2 group
    /*[^<pclass="mtZOt">]+*/ //todo 2 group
    /*[^href="\/\w+\/description\/element\/"><]+*/ //todo 1 group
    /*[^href="\/aries\/description\/element\/">]+*/ // TODO: group 2
    private String getDescription(ArrayList<String> list){
        Pattern pattern;
        Matcher matcher;
        StringBuilder builder = new StringBuilder();
        pattern = Pattern.compile("[^(<pclas=\"mtZO>\\/)]+");
        matcher = pattern.matcher(list.get(0));
        while(matcher.find()){
            builder.append(matcher.group());
        }
        pattern = Pattern.compile("[^<pclass=\"mtZOt\">]+");
        matcher = pattern.matcher(list.get(1));
        while(matcher.find()){
            builder.append(matcher.group());
        }
        pattern = Pattern.compile("[^href=\"\\/\\w+\\/description\\/element\\/\"><]+");
        matcher = pattern.matcher(list.get(2));
        while(matcher.find()){
            builder.append(matcher.group());
        }
        pattern = Pattern.compile("[^href=\"\\/\\w\\/description\\/element\\/\">]+");
        matcher = pattern.matcher(list.get(3));
        while(matcher.find()){
            builder.append(matcher.group());
        }
        return builder.toString();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
