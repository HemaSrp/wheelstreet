package com.wheelstreet.wheelstreet.model;

public class Data
{
    private String id;

    private String dataType;

    private String question;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDataType ()
    {
        return dataType;
    }

    public void setDataType (String dataType)
    {
        this.dataType = dataType;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", dataType = "+dataType+", question = "+question+"]";
    }
}
