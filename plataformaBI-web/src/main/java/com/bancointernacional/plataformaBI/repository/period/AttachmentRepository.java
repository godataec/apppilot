package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO;
import com.bancointernacional.plataformaBI.domain.model.period.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository <Attachment, UUID> {

   /* @Modifying
    @Transactional
    @Query(value = "DELETE FROM attachment WHERE process_id = :processId", nativeQuery = true)
    int deleteAllByProcessId(@Param("processId") String processId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM attachment WHERE process_policy_id = :processPolicyId", nativeQuery = true)
    int deleteAllByProcessPolicyId(@Param("processPolicyId") String processPolicyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM attachment WHERE policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    int deleteAllByPolicyQuestionaryId(@Param("policyQuestionaryId") String policyQuestionaryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM attachment WHERE user_answer_id = :userAnswerId", nativeQuery = true)
    int deleteAllByUserAnswerId(@Param("policyQuestionaryId") String userAnswerId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM attachment WHERE action_id = :processActionId", nativeQuery = true)
    int deleteAllByProcessActionId(@Param("policyQuestionaryId") String processActionId);*/

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId) " +
            "FROM Attachment a WHERE a.processId = :processId")
     List<AttachmentDTO> findByProcessId(@Param("processId") UUID processId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId, a.processPolicyId)" +
            " FROM Attachment a WHERE a.processPolicyId = :processPolicyId")
     List<AttachmentDTO> findByProcessPolicyId(@Param("processPolicyId") UUID processPolicyId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId, a.processPolicyId, a.policyQuestionaryId)  " +
            "FROM Attachment a " +
            "WHERE a.policyQuestionaryId = :policyQuestionaryId " +
            "AND a.userAnswerId IS NULL")
    List<AttachmentDTO> findByPolicyQuestionaryId(@Param("policyQuestionaryId") UUID policyQuestionaryId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId, a.processPolicyId, a.policyQuestionaryId)  " +
            "FROM Attachment a " +
            "WHERE a.userAnswerId = :userAnswerId")
    List<AttachmentDTO> findByUserAnswerId(@Param("userAnswerId")UUID userAnswerId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId, a.processPolicyId, a.policyQuestionaryId)  " +
            "FROM Attachment a " +
            "WHERE a.actionId = :actionId")
    List<AttachmentDTO> findByActionId(@Param("actionId")UUID actionId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId, a.processPolicyId, a.policyQuestionaryId, a.userAnswerId) " +
            "FROM Attachment a " +
            "WHERE a.policyQuestionaryId = :policyQuestionaryId " +
            "AND a.userAnswerId = :userAnswerId")
    List<AttachmentDTO> findByPolicyQuestionaryIdUserAnswerId(@Param("policyQuestionaryId") UUID policyQuestionaryId, @Param("userAnswerId") UUID userAnswerId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO( " +
       "a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.attachmentByte, " +
       "a.processId, a.processPolicyId, a.policyQuestionaryId, a.userAnswerId) " +  
       "FROM Attachment a " +
       "WHERE a.attachmentId = :attachmentId")
    Optional<AttachmentDTO> findByIdImagePreview(@Param("attachmentId") UUID attachmentId);

    @Query(value = "SELECT * FROM attachment WHERE attachment_id = :attachmentId", nativeQuery = true)
    Optional<Attachment> findByAttachmentId(@Param("attachmentId") String attachmentId);

    @Query("SELECT new com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO" +
            "(a.attachmentId, a.attachmentName, a.uploadDate, a.attachmentType, a.processId) " +
            "FROM Attachment a WHERE a.attachmentId = :attachmentId")
    Optional<AttachmentDTO> findInfoByIdFile(@Param("attachmentId") UUID attachmentId);


}
