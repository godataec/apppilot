/*==============================================================*/
/* DBMS name:       Microsoft SQL Server 2016                   */
/* Created on:      09/08/2025                                  */
/* Author:          yromero (v2)                                */
/*==============================================================*/

/*==============================================================*/
/* Table: BBI DROP CURRENT TABLES (v2)                          */
/*==============================================================*/

-- Drop all FKs referencing subsidiary before dropping subsidiary
IF OBJECT_ID('FK_ColorPallete_Subsidiary', 'F') IS NOT NULL
ALTER TABLE color_pallete DROP CONSTRAINT FK_ColorPallete_Subsidiary;
GO

IF OBJECT_ID('FK_User_Subsidiary', 'F') IS NOT NULL
ALTER TABLE subsidiary_user DROP CONSTRAINT FK_User_Subsidiary;
GO

IF OBJECT_ID('FK_Process_Subsidiary', 'F') IS NOT NULL
ALTER TABLE process DROP CONSTRAINT FK_Process_Subsidiary;
GO

-- Drop other FKs
IF OBJECT_ID('FK_Questionary_Policy', 'F') IS NOT NULL
ALTER TABLE questionary DROP CONSTRAINT FK_Questionary_Policy;
GO
IF OBJECT_ID('FK_Question_Questionary', 'F') IS NOT NULL
ALTER TABLE question DROP CONSTRAINT FK_Question_Questionary;
GO
IF OBJECT_ID('FK_Option_Question', 'F') IS NOT NULL
ALTER TABLE question_option DROP CONSTRAINT FK_Option_Question;
GO
IF OBJECT_ID('FK_Action_Process', 'F') IS NOT NULL
ALTER TABLE process_action DROP CONSTRAINT FK_Action_Process;
GO
IF OBJECT_ID('FK_UserAnswer_UserQuestion', 'F') IS NOT NULL
ALTER TABLE user_answer DROP CONSTRAINT FK_UserAnswer_UserQuestion;
GO
IF OBJECT_ID('FK_ProcessPolicy_Process', 'F') IS NOT NULL
ALTER TABLE process_policy DROP CONSTRAINT FK_ProcessPolicy_Process;
GO
IF OBJECT_ID('FK_PolicyQuestionary_ProcessPolicy', 'F') IS NOT NULL
ALTER TABLE policy_questionary DROP CONSTRAINT FK_PolicyQuestionary_ProcessPolicy;
GO
IF OBJECT_ID('FK_UserQuestion_PolicyQuestionary', 'F') IS NOT NULL
ALTER TABLE user_question DROP CONSTRAINT FK_UserQuestion_PolicyQuestionary;
GO
IF OBJECT_ID('FK_UserAnswer_UserQuestion', 'F') IS NOT NULL
ALTER TABLE user_answer DROP CONSTRAINT FK_UserAnswer_UserQuestion;
GO

-- Drop tables
IF OBJECT_ID('policy', 'U') IS NOT NULL DROP TABLE policy;
GO
IF OBJECT_ID('questionary', 'U') IS NOT NULL DROP TABLE questionary;
GO
IF OBJECT_ID('question', 'U') IS NOT NULL DROP TABLE question;
GO
IF OBJECT_ID('question_option', 'U') IS NOT NULL DROP TABLE question_option;
GO
IF OBJECT_ID('subsidiary', 'U') IS NOT NULL DROP TABLE subsidiary;
GO
IF OBJECT_ID('color_pallete', 'U') IS NOT NULL DROP TABLE color_pallete;
GO
IF OBJECT_ID('subsidiary_user', 'U') IS NOT NULL DROP TABLE subsidiary_user;
GO
IF OBJECT_ID('process', 'U') IS NOT NULL DROP TABLE process;
GO
IF OBJECT_ID('process_action', 'U') IS NOT NULL DROP TABLE process_action;
GO
IF OBJECT_ID('process_policy', 'U') IS NOT NULL DROP TABLE process_policy;
GO
IF OBJECT_ID('policy_questionary', 'U') IS NOT NULL DROP TABLE policy_questionary;
GO
IF OBJECT_ID('user_question', 'U') IS NOT NULL DROP TABLE user_question;
GO
IF OBJECT_ID('user_answer', 'U') IS NOT NULL DROP TABLE user_answer;
GO
IF OBJECT_ID('attachment', 'U') IS NOT NULL DROP TABLE attachment;
GO

/*==============================================================*/
/* Table: BBI TEMPLATE SOURCE                                   */
/*==============================================================*/

/*==============================================================*/
/* Table: Policy                                                */
/*==============================================================*/

IF OBJECT_ID('policy', 'U') IS NOT NULL DROP TABLE policy;
GO
CREATE TABLE policy
(
    policy_id          int not null PRIMARY KEY,
    policy_name        varchar(50) null,
    created_at         datetime null,
    updated_at         datetime null,
    policy_status      varchar(20) null,
    policy_description varchar(100) null,
    is_deleted         BIT DEFAULT 0,
)
GO

/*==============================================================*/
/* Table: Questionary                                           */
/*==============================================================*/

IF OBJECT_ID('questionary', 'U') IS NOT NULL DROP TABLE questionary;
GO
CREATE TABLE questionary
(
    questionary_id     int          not null PRIMARY KEY,
    policy_id          int          not null,
    questionary_name   varchar(100) not null,
    description        varchar(100) not null,
    created_at         datetime null,
    questionary_status varchar(20)  not null,
    source_file        varchar(50) null,
    total_number       int null,
    is_deleted         BIT DEFAULT 0,
)
GO

ALTER TABLE [questionary]
    ADD CONSTRAINT FK_Questionary_Policy
    FOREIGN KEY (policy_id)
    REFERENCES policy(policy_id);
GO

CREATE INDEX IX_Questionary_PolicyId
    ON questionary (policy_id);
GO

/*==============================================================*/
/* Table: Question                                              */
/*==============================================================*/

IF OBJECT_ID('question', 'U') IS NOT NULL DROP TABLE question;
GO
CREATE TABLE question
(
    question_id    int         not null PRIMARY KEY,
    questionary_id int         not null,
    question_type  varchar(20) not null,
    description    varchar(900) null,
    question_text  varchar(1000) null,
    question_json  varchar(4000) null,
    parent_id      int null,
    document_id    varchar(50) null,
    is_deleted     BIT DEFAULT 0,
)
GO

ALTER TABLE [question]
    ADD CONSTRAINT FK_Question_Questionary
    FOREIGN KEY (questionary_id)
    REFERENCES questionary(questionary_id);
GO


CREATE INDEX IX_Question_QuestionaryId
    ON question (questionary_id);
GO

CREATE INDEX IX_Question_DocumentId
    ON question (document_id);
GO

CREATE INDEX IX_Question_ParentId
    ON question (parent_id);
GO

/*==============================================================*/
/* Table: Option                                                */
/*==============================================================*/

IF OBJECT_ID('question_option', 'U') IS NOT NULL DROP TABLE question_option;
GO
CREATE TABLE question_option
(
    option_id   UNIQUEIDENTIFIER DEFAULT NEWID() not null PRIMARY KEY,
    question_id int                              not null,
    option_text varchar(2000) null,
    created_at  datetime null,
    updated_at  datetime null,
    is_deleted  BIT              DEFAULT 0,
)
    GO

ALTER TABLE [question_option]
    ADD CONSTRAINT FK_Option_Question
    FOREIGN KEY (question_id)
    REFERENCES question(question_id);
GO

CREATE INDEX IX_QuestionOption_QuestionId
    ON question_option (question_id);
GO
/*==============================================================*/
/* Table: BBI SETTINGS                                          */
/*==============================================================*/

/*==============================================================*/
/* Table: Subsidiary                                            */
/*==============================================================*/
IF OBJECT_ID('subsidiary', 'U') IS NOT NULL DROP TABLE subsidiary;
GO
CREATE TABLE subsidiary
(
    subsidiary_id INT IDENTITY(1,1)   PRIMARY KEY,
    group_name    varchar(100) NOT NULL,
    description   varchar(255) NULL,
    is_deleted    BIT DEFAULT 0,
    created_at    datetime NULL,
    updated_at     datetime NULL,
);
GO

/*==============================================================*/
/* Table: ColorPallete                                          */
/*==============================================================*/
IF OBJECT_ID('color_pallete', 'U') IS NOT NULL DROP TABLE color_pallete;
GO
CREATE TABLE color_pallete
(
    pallete_id    INT IDENTITY(1,1)   PRIMARY KEY,
    pallete_name  varchar(100) NOT NULL,
    hex_value     varchar(7)   NOT NULL,
    description   varchar(255) NULL,
    pallete_type  varchar(255) NULL,
    is_deleted    BIT DEFAULT 0,
    created_at    datetime NULL,
    updated_at     datetime NULL,
    subsidiary_id INT          NOT NULL,
);
GO

ALTER TABLE [color_pallete]
    ADD CONSTRAINT FK_ColorPallete_Subsidiary
    FOREIGN KEY (subsidiary_id)
    REFERENCES subsidiary(subsidiary_id);
GO

CREATE INDEX IX_ColorPallete_SubsidiaryId
    ON color_pallete (subsidiary_id);
GO
/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
IF OBJECT_ID('subsidiary_user', 'U') IS NOT NULL DROP TABLE subsidiary_user;
GO
CREATE TABLE subsidiary_user
(
    user_id         UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    user_name       varchar(100)    NOT NULL,
    user_last_name  varchar(255)    NULL,
    open_id_user    varchar(50)     NOT NULL,
    user_status     varchar(20)     NULL,
    role            varchar(50)     NULL,
    email           varchar(50)     NULL,
    is_deleted      BIT             DEFAULT 0,
    created_at      datetime        NULL,
    updated_at      datetime        NULL,
    subsidiary_id   INT             NOT NULL,
);
GO

ALTER TABLE [subsidiary_user]
    ADD CONSTRAINT FK_User_Subsidiary
    FOREIGN KEY (subsidiary_id)
    REFERENCES subsidiary(subsidiary_id);
GO

CREATE INDEX IX_SubsidiaryUser_SubsidiaryId
    ON subsidiary_user (subsidiary_id);
GO

/*==============================================================*/
/* Table: BI BUSINESS ASSIGNATIONS                              */
/*==============================================================*/

/*==============================================================*/
/* Table: PROCESS                                               */
/*==============================================================*/
IF OBJECT_ID('process', 'U') IS NOT NULL DROP TABLE process;
GO
create table process
(
    process_id          UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    process_description varchar(100)                     NOT NULL,
    start_date          datetime                         NOT NULL,
    end_date            datetime                         NOT NULL,
    process_status      varchar(20)                      NOT NULL,
    is_deleted          BIT                              DEFAULT 0,
    reopened            BIT                              DEFAULT 0,
    created_at          datetime                         NULL,
    updated_at          datetime                         NULL,
    subsidiary_id       INT                              NOT NULL,
)
GO

ALTER TABLE [process]
    ADD CONSTRAINT FK_Process_Subsidiary
    FOREIGN KEY (subsidiary_id)
    REFERENCES subsidiary(subsidiary_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Process_SubsidiaryId
    ON process (subsidiary_id);
GO

/*==============================================================*/
/* Table: PROCESS ACTION                                        */
/*==============================================================*/
IF OBJECT_ID('process_action', 'U') IS NOT NULL DROP TABLE process;
GO
create table process_action
(
    action_id           UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    action_reason       varchar(100)                     NOT NULL,
    action_type         varchar(20)                      NOT NULL,
    is_deleted          BIT                              DEFAULT 0,
    created_at          datetime                         NULL,
    updated_at          datetime                         NULL,
    process_id          UNIQUEIDENTIFIER                 NOT NULL,
)
GO

ALTER TABLE [process_action]
    ADD CONSTRAINT FK_Action_Process
    FOREIGN KEY (process_id)
    REFERENCES process(process_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Action_ProcessId
    ON process_action (process_id);
GO


/*==============================================================*/
/* Table: PROCESS_POLICY                                        */
/*==============================================================*/
IF OBJECT_ID('process_policy', 'U') IS NOT NULL DROP TABLE process_policy;
GO
create table process_policy
(
    process_policy_id       UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    start_date              datetime                         NULL,
    end_date                datetime                         NULL,
    process_policy_status   varchar(20)                      NOT NULL,
    is_deleted              BIT              DEFAULT 0,
    created_at              datetime NULL,
    updated_at              datetime NULL,
    process_id              UNIQUEIDENTIFIER                 NOT NULL,
    policy_id               INT                              NOT NULL,
);
GO

ALTER TABLE [process_policy]
    ADD CONSTRAINT FK_ProcessPolicy_Process
    FOREIGN KEY (process_id)
    REFERENCES process(process_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_ProcessPolicy_PolicyId
    ON process_policy (policy_id);
GO


/*==============================================================*/
/* Table: POLICY_QUESTIONARY                                    */
/*==============================================================*/
IF OBJECT_ID('policy_questionary', 'U') IS NOT NULL DROP TABLE policy_questionary;
GO
create table policy_questionary
(
    policy_questionary_id       UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    start_date                  datetime                         NOT NULL,
    end_date                    datetime                         NOT NULL,
    policy_questionary_status   varchar(20)                      NOT NULL,
    is_deleted                  BIT              DEFAULT 0,
    created_at                  datetime NULL,
    updated_at                  datetime NULL,
    questionary_id              INT                              NOT NULL,
    process_policy_id           UNIQUEIDENTIFIER                 NOT NULL,
);
GO

ALTER TABLE [policy_questionary]
    ADD CONSTRAINT FK_PolicyQuestionary_ProcessPolicy
    FOREIGN KEY (process_policy_id)
    REFERENCES process_policy(process_policy_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_PolicyQuestionary_QuestionaryId
    ON policy_questionary (questionary_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_PolicyQuestionary_ProcessPolicyId
    ON policy_questionary (process_policy_id);
GO

/*==============================================================*/
/* Table: USER_QUESTION                                         */
/*==============================================================*/
IF OBJECT_ID('user_question', 'U') IS NOT NULL DROP TABLE user_question;
GO
create table user_question
(
    user_question_id        UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    question_id             INT                              NOT NULL,
    user_assigned           UNIQUEIDENTIFIER                 NULL,
    question_status         varchar(20)                      NOT NULL,
    is_deleted              BIT              DEFAULT 0,
    created_at              datetime NULL,
    updated_at              datetime NULL,
    policy_questionary_id   UNIQUEIDENTIFIER                 NOT NULL,
);
GO

/* FOREING KEY                                                  */
ALTER TABLE [user_question]
    ADD CONSTRAINT FK_UserQuestion_PolicyQuestionary
    FOREIGN KEY (policy_questionary_id)
    REFERENCES policy_questionary(policy_questionary_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_UserQuestion_UserAssigned
    ON user_question (user_assigned);
GO

/*==============================================================*/
/* Table: USER_ANSWER                                           */
/*==============================================================*/
IF OBJECT_ID('user_answer', 'U') IS NOT NULL DROP TABLE user_answer;
GO
create table user_answer
(
    user_answer_id          UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    question_id             INT                              NOT NULL,
    policy_questionary_id   UNIQUEIDENTIFIER                 NULL,
    user_assigned           UNIQUEIDENTIFIER                 NULL,
    user_question_id        UNIQUEIDENTIFIER                 NULL,
    created_at              datetime            NULL,
    updated_at              datetime            NULL,
    answer_status           varchar(20)         NULL,
    document_id             varchar(50)         NULL,
    updated_by              UNIQUEIDENTIFIER                 NULL,
    created_by              UNIQUEIDENTIFIER                 NULL,
    is_deleted              BIT                 DEFAULT 0,
    answer_text             NVARCHAR(MAX)       NULL,
    answer_type             NVARCHAR(50)        NULL,
    answer_description      NVARCHAR(MAX)       NULL,
)
GO

/* FOREING KEY                                                  */
ALTER TABLE [user_answer]
    ADD CONSTRAINT FK_UserAnswer_UserQuestion
    FOREIGN KEY (user_question_id)
    REFERENCES user_question(user_question_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_UserAnswer_UserAssigned
    ON user_answer (user_assigned);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_UserAnswer_PolicyQuestionary
    ON user_answer (policy_questionary_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_UserAnswer_UserQuestion
    ON user_answer (user_question_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_UserAnswer_QUestionId
    ON user_answer (question_id);
GO

/*==============================================================*/
/* Table:  ATTACHMENT                                           */
/*==============================================================*/
IF OBJECT_ID('attachment', 'U') IS NOT NULL DROP TABLE attachment;
GO
create table attachment
(
    attachment_id           UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    attachment_name         varchar(200)                     NOT NULL,
    attachment_type         varchar(200) NULL,
    upload_date             varchar(20)                      NOT NULL,
    attachment_byte         varbinary(MAX)      FILESTREAM NOT NULL,
    attachment_preview_byte varbinary(MAX)      FILESTREAM NULL,
    process_id              UNIQUEIDENTIFIER NULL,
    action_id               UNIQUEIDENTIFIER NULL,
    process_policy_id       UNIQUEIDENTIFIER NULL,
    policy_questionary_id   UNIQUEIDENTIFIER NULL,
    user_answer_id          UNIQUEIDENTIFIER NULL,
    is_deleted              BIT              DEFAULT 0,
    FileGuid                UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL    DEFAULT NEWID(), -- Genera un GUID único automáticamente
    constraint UQ_FileGuid UNIQUE (FileGuid)                                         -- FileGuid es único (obligatorio para FILESTREAM)
)
    GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Attachment_Process
    ON attachment (process_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Attachment_Process_Action
    ON attachment (action_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Attachment_ProcessPolicy
    ON attachment (process_policy_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Attachment_PolicyQuestionary
    ON attachment (policy_questionary_id);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_Attachment_UserAnswer
    ON attachment (user_answer_id);
GO

/*==============================================================*/
/* Table: AUDIT_HISTORY                                         */
/*==============================================================*/
IF OBJECT_ID('audit_history', 'U') IS NOT NULL DROP TABLE audit_history;
GO
create table audit_history
(
    audit_id                UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL PRIMARY KEY,
    entity_name             varchar(50)                               NOT NULL,
    action                  varchar(20)                  NULL,
    entity_id                varchar(50)                      NOT NULL,
    json_request             varchar(MAX)                      NOT NULL,
    action_date              datetime NULL,
    is_deleted              BIT              DEFAULT 0,
    created_at              datetime NULL,
);
GO

/* INDEX KEY                                                    */
CREATE INDEX IX_AuditHistory_EntityId
    ON audit_history (entity_id);
GO