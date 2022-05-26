package com.example.depresol.messages;

public class MessagesList {
    private String name, mobile, lastMessage;

    private int unseenMessages;

    public MessagesList(String name, String mobile, String lastMessage, int unseenMessages) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
