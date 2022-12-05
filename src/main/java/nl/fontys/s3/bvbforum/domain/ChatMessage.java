package nl.fontys.s3.bvbforum.domain;

import lombok.Data;

@Data
public class ChatMessage {
    private String id;
    private String from;
    private String text;
}
