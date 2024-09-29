package org.raghav.smartcontactmanager.Helpers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Message {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.blue;
}
