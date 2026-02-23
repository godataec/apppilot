package com.bancointernacional.plataformaBI.domain.model.period;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="attachment")
public class Attachment {
    @Id
    @Column(name = "attachment_id", nullable = false, columnDefinition = "uniqueidentifier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID attachmentId;

    @NotBlank
    @Size(max = 50)
    @Column(name= "attachment_name")
    private String attachmentName;

    @Column(name="upload_date")
    private LocalDate uploadDate;

    @NotBlank
    @Size(max = 200)
    @Column(name="attachment_type")
    private String attachmentType;

    @Lob
    @Column(name = "attachment_byte", columnDefinition = "VARBINARY(MAX) FILESTREAM NOT NULL")
    private byte[] attachmentByte;

    @Lob
    @Column(name = "attachment_preview_byte", columnDefinition = "VARBINARY(MAX) FILESTREAM NOT NULL")
    private byte[] attachmentPreviewByte;

    @Column(name = "process_id")
    private UUID processId;

    @Column(name = "process_policy_id")
    private UUID processPolicyId;

    @Column(name = "policy_questionary_id")
    private UUID policyQuestionaryId;

    @Column(name = "user_answer_id" )
    private UUID userAnswerId;

    @Column(name = "action_id" )
    private UUID actionId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
