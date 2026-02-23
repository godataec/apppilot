/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2016                    */
/* Created on:     16/11/2024 11:46:12                          */
/*==============================================================*/


if
exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANSWER') and o.name = 'FK_ANSWER_RELATIONS_QUESTION')
alter table ANSWER
drop
constraint FK_ANSWER_RELATIONS_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANSWER') and o.name = 'FK_ANSWER_RELATIONS_USERS')
alter table ANSWER
drop
constraint FK_ANSWER_RELATIONS_USERS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANSWER') and o.name = 'FK_ANSWER_RELATIONS_PROCESSQ')
alter table ANSWER
drop
constraint FK_ANSWER_RELATIONS_PROCESSQ
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ATTACHFILE') and o.name = 'FK_ATTACHFI_HAVE1_PROCESSM')
alter table ATTACHFILE
drop
constraint FK_ATTACHFI_HAVE1_PROCESSM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ATTACHFILE') and o.name = 'FK_ATTACHFI_HAVE2_PROCESSI')
alter table ATTACHFILE
drop
constraint FK_ATTACHFI_HAVE2_PROCESSI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ATTACHFILE') and o.name = 'FK_ATTACHFI_RELATIONS_ANSWER')
alter table ATTACHFILE
drop
constraint FK_ATTACHFI_RELATIONS_ANSWER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ATTACHFILE') and o.name = 'FK_ATTACHFI_RELATIONS_PROCESSQ')
alter table ATTACHFILE
drop
constraint FK_ATTACHFI_RELATIONS_PROCESSQ
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ATTACHFILE') and o.name = 'FK_ATTACHFI_RELATIONS_QUESTION')
alter table ATTACHFILE
drop
constraint FK_ATTACHFI_RELATIONS_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('OPTIONS') and o.name = 'FK_OPTIONS_QUESTIONX_QUESTION')
alter table OPTIONS
drop
constraint FK_OPTIONS_QUESTIONX_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PROCESSINSURANCEPOLICY') and o.name = 'FK_PROCESSI_HAVE_PROCESSM')
alter table PROCESSINSURANCEPOLICY
drop
constraint FK_PROCESSI_HAVE_PROCESSM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PROCESSINSURANCEPOLICY') and o.name = 'FK_PROCESSI_RELATIONS_INSURANC')
alter table PROCESSINSURANCEPOLICY
drop
constraint FK_PROCESSI_RELATIONS_INSURANC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PROCESSQUESTIONARY') and o.name = 'FK_PROCESSQ_RELATIONS_PROCESSI')
alter table PROCESSQUESTIONARY
drop
constraint FK_PROCESSQ_RELATIONS_PROCESSI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PROCESSQUESTIONARY') and o.name = 'FK_PROCESSQ_RELATIONS_QUESTION')
alter table PROCESSQUESTIONARY
drop
constraint FK_PROCESSQ_RELATIONS_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('QUESTION') and o.name = 'FK_QUESTION_QUESTIONN_QUESTION')
alter table QUESTION
drop
constraint FK_QUESTION_QUESTIONN_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('QUESTIONNAIRE') and o.name = 'FK_QUESTION_RELATIONS_INSURANC')
alter table QUESTIONNAIRE
drop
constraint FK_QUESTION_RELATIONS_INSURANC
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ANSWER')
            and   name  = 'RELATIONSHIP_15_FK'
            and   indid > 0
            and   indid < 255)
drop index ANSWER.RELATIONSHIP_15_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ANSWER')
            and   name  = 'FK_ANSWER_RELATIONS_QUESTION_FK'
            and   indid > 0
            and   indid < 255)
drop index ANSWER.FK_ANSWER_RELATIONS_QUESTION_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ANSWER')
            and   name  = 'FK_ANSWER_RELATIONS_USERS_FK'
            and   indid > 0
            and   indid < 255)
drop index ANSWER.FK_ANSWER_RELATIONS_USERS_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ANSWER')
            and   type = 'U')
drop table ANSWER
    go
    if exists (select 1
    from sysindexes
    where id = object_id('ATTACHFILE')
    and name = 'FK_ATTACHFI_RELATIONS_ANSWER_FK'
    and indid > 0
    and indid < 255)
drop index ATTACHFILE.FK_ATTACHFI_RELATIONS_ANSWER_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ATTACHFILE')
            and   name  = 'FK_ATTACHFI_RELATIONS_PROCESSQ_FK'
            and   indid > 0
            and   indid < 255)
drop index ATTACHFILE.FK_ATTACHFI_RELATIONS_PROCESSQ_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ATTACHFILE')
            and   name  = 'FK_ATTACHFI_RELATIONS_QUESTION_FK'
            and   indid > 0
            and   indid < 255)
drop index ATTACHFILE.FK_ATTACHFI_RELATIONS_QUESTION_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ATTACHFILE')
            and   name  = 'FK_ATTACHFI_HAVE2_PROCESSI_FK'
            and   indid > 0
            and   indid < 255)
drop index ATTACHFILE.FK_ATTACHFI_HAVE2_PROCESSI_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ATTACHFILE')
            and   name  = 'FK_ATTACHFI_HAVE1_PROCESSM_FK'
            and   indid > 0
            and   indid < 255)
drop index ATTACHFILE.FK_ATTACHFI_HAVE1_PROCESSM_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ATTACHFILE')
            and   type = 'U')
drop table ATTACHFILE
    go
    if exists (select 1
    from sysobjects
    where id = object_id('INSURANCEPOLICY')
    and type = 'U')
drop table INSURANCEPOLICY
    go
    if exists (select 1
    from sysindexes
    where id = object_id('OPTIONS')
    and name = 'FK_OPTIONS_QUESTIONX_QUESTION_FK'
    and indid > 0
    and indid < 255)
drop index OPTIONS.FK_OPTIONS_QUESTIONX_QUESTION_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPTIONS')
            and   type = 'U')
drop table OPTIONS
    go
    if exists (select 1
    from sysindexes
    where id = object_id('PROCESSINSURANCEPOLICY')
    and name = 'FK_PROCESSI_RELATIONS_INSURANC_FK'
    and indid > 0
    and indid < 255)
drop index PROCESSINSURANCEPOLICY.FK_PROCESSI_RELATIONS_INSURANC_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PROCESSINSURANCEPOLICY')
            and   name  = 'FK_PROCESSI_HAVE_PROCESSM_FK'
            and   indid > 0
            and   indid < 255)
drop index PROCESSINSURANCEPOLICY.FK_PROCESSI_HAVE_PROCESSM_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PROCESSINSURANCEPOLICY')
            and   type = 'U')
drop table PROCESSINSURANCEPOLICY
    go
    if exists (select 1
    from sysobjects
    where id = object_id('PROCESSMANAGER')
    and type = 'U')
drop table PROCESSMANAGER
    go
    if exists (select 1
    from sysindexes
    where id = object_id('PROCESSQUESTIONARY')
    and name = 'FK_PROCESSQ_RELATIONS_PROCESSI_FK'
    and indid > 0
    and indid < 255)
drop index PROCESSQUESTIONARY.FK_PROCESSQ_RELATIONS_PROCESSI_FK 
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PROCESSQUESTIONARY')
            and   name  = 'FK_PROCESSQ_RELATIONS_QUESTION_FK'
            and   indid > 0
            and   indid < 255)
drop index PROCESSQUESTIONARY.FK_PROCESSQ_RELATIONS_QUESTION_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PROCESSQUESTIONARY')
            and   type = 'U')
drop table PROCESSQUESTIONARY
    go
    if exists (select 1
    from sysindexes
    where id = object_id('QUESTION')
    and name = 'FK_QUESTION_QUESTIONN_QUESTION_FK'
    and indid > 0
    and indid < 255)
drop index QUESTION.FK_QUESTION_QUESTIONN_QUESTION_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('QUESTION')
            and   type = 'U')
drop table QUESTION
    go
    if exists (select 1
    from sysindexes
    where id = object_id('QUESTIONNAIRE')
    and name = 'RELATIONSHIP_14_FK'
    and indid > 0
    and indid < 255)
drop index QUESTIONNAIRE.RELATIONSHIP_14_FK 
go

if exists (select 1
            from  sysobjects
           where  id = object_id('QUESTIONNAIRE')
            and   type = 'U')
drop table QUESTIONNAIRE
    go
    if exists (select 1
    from sysobjects
    where id = object_id('USERS')
    and type = 'U')
drop table USERS
    go

/*==============================================================*/
/* Table: ANSWER                                                */
/*==============================================================*/
create table ANSWER
(
    IDANSWER   UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    IDQUESTION int                              not null,
    IDUSER     UNIQUEIDENTIFIER DEFAULT NEWID() null,
    IDQUEST    UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    ANSWERTEXT varchar(MAX) null,
    CREATEDAT  datetime null,
    UPDATEDAT  datetime null,
    STATUS     varchar(20) null,
    DOCUMENTID varchar(50)                      not null,
    UPDATEDBY  UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    ISDELETED BIT DEFAULT 0,
    constraint PK_ANSWER primary key (IDANSWER)
)
    go

/*==============================================================*/
/* Index: FK_ANSWER_RELATIONS_USERS_FK                          */
/*==============================================================*/



create
nonclustered index FK_ANSWER_RELATIONS_USERS_FK on ANSWER (IDUSER ASC)
go

/*==============================================================*/
/* Index: FK_ANSWER_RELATIONS_QUESTION_FK                       */
/*==============================================================*/




create
nonclustered index FK_ANSWER_RELATIONS_QUESTION_FK on ANSWER (IDQUESTION ASC)
go

/*==============================================================*/
/* Index: RELATIONSHIP_15_FK                                    */
/*==============================================================*/




create
nonclustered index RELATIONSHIP_15_FK on ANSWER (IDQUEST ASC)
go

/*==============================================================*/
/* Table: ATTACHFILE                                            */
/*==============================================================*/
create table ATTACHFILE
(
    IDFILE         UNIQUEIDENTIFIER DEFAULT NEWID() not null,                 -- Clave primaria
    IDQUESTION     int null,
    IDANSWER       UNIQUEIDENTIFIER null,
    IDPOLICY       numeric null,
    IDQUEST        UNIQUEIDENTIFIER null,
    IDPROCESS      UNIQUEIDENTIFIER null,
    NAMEFILE       varchar(200)                     not null,
    DATEUPLOADFILE varchar(20)                      not null,
    DATAFILE       varbinary(MAX)       FILESTREAM not null,                  -- Columna FILESTREAM
    PHOTOPREVIEW   varbinary(MAX)       FILESTREAM null,
    ISDELETED      BIT DEFAULT 0,
    TYPEFILE       varchar(200) null,
    FileGuid       UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL
                         DEFAULT NEWID(), -- Genera un GUID único automáticamente
    constraint PK_ATTACHFILE primary key (IDFILE),                            -- IDFILE sigue siendo la clave primaria
    constraint UQ_FileGuid UNIQUE (FileGuid)                                  -- FileGuid es único (obligatorio para FILESTREAM)
)
    go

/*==============================================================*/
/* Index: FK_ATTACHFI_HAVE1_PROCESSM_FK                         */
/*==============================================================*/



create
nonclustered index FK_ATTACHFI_HAVE1_PROCESSM_FK on ATTACHFILE (IDPROCESS ASC)
go

/*==============================================================*/
/* Index: FK_ATTACHFI_HAVE2_PROCESSI_FK                         */
/*==============================================================*/




create
nonclustered index FK_ATTACHFI_HAVE2_PROCESSI_FK on ATTACHFILE (IDPOLICY ASC)
go

/*==============================================================*/
/* Index: FK_ATTACHFI_RELATIONS_QUESTION_FK                     */
/*==============================================================*/




create
nonclustered index FK_ATTACHFI_RELATIONS_QUESTION_FK on ATTACHFILE (IDQUESTION ASC)
go

/*==============================================================*/
/* Index: FK_ATTACHFI_RELATIONS_PROCESSQ_FK                     */
/*==============================================================*/




create
nonclustered index FK_ATTACHFI_RELATIONS_PROCESSQ_FK on ATTACHFILE (IDQUEST ASC)
go

/*==============================================================*/
/* Index: FK_ATTACHFI_RELATIONS_ANSWER_FK                       */
/*==============================================================*/




create
nonclustered index FK_ATTACHFI_RELATIONS_ANSWER_FK on ATTACHFILE (IDANSWER ASC)
go

/*==============================================================*/
/* Table: INSURANCEPOLICY                                       */
/*==============================================================*/
create table INSURANCEPOLICY
(
    IDINSURANCE   int not null,
    NAMEINSURANCE varchar(50) null,
    CREATEDAT     datetime null,
    UPDATEDAT     datetime null,
    STATUS        varchar(20) null,
    DESCRIPTION   varchar(100) null,
    ISDELETED     BIT DEFAULT 0,
    constraint PK_INSURANCEPOLICY primary key (IDINSURANCE)
)
    go

/*==============================================================*/
/* Table: OPTIONS                                               */
/*==============================================================*/
create table OPTIONS
(
    IDOPTION   UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    IDQUESTION int                              not null,
    OPTIONTEXT varchar(2000) null,
    CREATEDAT  datetime null,
    ISDELETED  BIT DEFAULT 0,
    constraint PK_OPTIONS primary key (IDOPTION)
)
    go

/*==============================================================*/
/* Index: FK_OPTIONS_QUESTIONX_QUESTION_FK                      */
/*==============================================================*/



create
nonclustered index FK_OPTIONS_QUESTIONX_QUESTION_FK on OPTIONS (IDQUESTION ASC)
go

/*==============================================================*/
/* Table: PROCESSINSURANCEPOLICY                                */
/*==============================================================*/
create table PROCESSINSURANCEPOLICY
(
    IDPOLICY    numeric identity,
    IDINSURANCE int      not null,
    IDPROCESS   UNIQUEIDENTIFIER null,
    DATEBEGIN   datetime not null,
    DATEEND     datetime not null,
    STATUS      varchar(20) null,
    ISDELETED   BIT DEFAULT 0,
    constraint PK_PROCESSINSURANCEPOLICY primary key (IDPOLICY)
)
    go

/*==============================================================*/
/* Index: FK_PROCESSI_HAVE_PROCESSM_FK                          */
/*==============================================================*/



create
nonclustered index FK_PROCESSI_HAVE_PROCESSM_FK on PROCESSINSURANCEPOLICY (IDPROCESS ASC)
go

/*==============================================================*/
/* Index: FK_PROCESSI_RELATIONS_INSURANC_FK                     */
/*==============================================================*/




create
nonclustered index FK_PROCESSI_RELATIONS_INSURANC_FK on PROCESSINSURANCEPOLICY (IDINSURANCE ASC)
go

/*==============================================================*/
/* Table: PROCESSMANAGER                                        */
/*==============================================================*/
create table PROCESSMANAGER
(
    IDPROCESS          UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    PROCESSDESCRIPTION varchar(100)                     not null,
    DATEBEGIN          datetime                         not null,
    DATEEND            datetime                         not null,
    STATUS             varchar(20)                      not null,
    ISDELETED          BIT DEFAULT 0,
    constraint PK_PROCESSMANAGER primary key (IDPROCESS)
)
    go

/*==============================================================*/
/* Table: PROCESSQUESTIONARY                                    */
/*==============================================================*/
create table PROCESSQUESTIONARY
(
    IDQUEST         UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    IDQUESTIONNAIRE int                              not null,
    IDPOLICY        numeric null,
    DATEBEGINQUEST  datetime                         not null,
    DATEENDQUEST    datetime null,
    STATUS          varchar(20) null,
    ISDELETED       BIT DEFAULT 0,
    constraint PK_PROCESSQUESTIONARY primary key (IDQUEST)
)
    go

/*==============================================================*/
/* Index: FK_PROCESSQ_RELATIONS_QUESTION_FK                     */
/*==============================================================*/



create
nonclustered index FK_PROCESSQ_RELATIONS_QUESTION_FK on PROCESSQUESTIONARY (IDQUESTIONNAIRE ASC)
go

/*==============================================================*/
/* Index: FK_PROCESSQ_RELATIONS_PROCESSI_FK                     */
/*==============================================================*/




create
nonclustered index FK_PROCESSQ_RELATIONS_PROCESSI_FK on PROCESSQUESTIONARY (IDPOLICY ASC)
go

/*==============================================================*/
/* Table: QUESTION                                              */
/*==============================================================*/
create table QUESTION
(
    IDQUESTION          int         not null,
    IDQUESTIONNAIRE     int         not null,
    TYPEQUESTION        varchar(20) not null,
    DESCRIPTIONQUESTION varchar(900) null,
    QUESTIONTEXT        varchar(1000) null,
    QUESTIONJSON        varchar(4000) null,
    PARENTID            int null,
    DOCUMENTID          varchar(50) null,
    ISDELETED           BIT DEFAULT 0,
    constraint PK_QUESTION primary key (IDQUESTION)
)
    go

/*==============================================================*/
/* Index: FK_QUESTION_QUESTIONN_QUESTION_FK                     */
/*==============================================================*/



create
nonclustered index FK_QUESTION_QUESTIONN_QUESTION_FK on QUESTION (IDQUESTIONNAIRE ASC)
go

/*==============================================================*/
/* Table: QUESTIONNAIRE                                         */
/*==============================================================*/
create table QUESTIONNAIRE
(
    IDQUESTIONNAIRE   int          not null,
    IDINSURANCE       int          not null,
    NAMEQUESTIONNAIRE varchar(100) not null,
    DESCRIPTION       varchar(100) not null,
    CREATEDAT         datetime null,
    STATUS            varchar(20)  not null,
    SOURCEFILE        varchar(50) null,
    TOTALQUESTION     int null,
    ISDELETED         BIT DEFAULT 0,
    constraint PK_QUESTIONNAIRE primary key (IDQUESTIONNAIRE)
)
    go

/*==============================================================*/
/* Index: RELATIONSHIP_14_FK                                    */
/*==============================================================*/



create
nonclustered index RELATIONSHIP_14_FK on QUESTIONNAIRE (IDINSURANCE ASC)
go

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS
(
    IDUSER     UNIQUEIDENTIFIER DEFAULT NEWID() not null,
    OPENIDUSER varchar(50)                      not null,
    NAME       varchar(50)                      not null,
    LASTNAME   varchar(50)                      not null,
    STATUS     varchar(20)                      not null,
    ROLE       varchar(50)                      not null,
    EMAIL      varchar(50)                      not null,
    ISDELETED  BIT DEFAULT 0,
    constraint PK_USERS primary key (IDUSER)
)
    go

alter table ANSWER
    add constraint FK_ANSWER_RELATIONS_QUESTION foreign key (IDQUESTION)
        references QUESTION (IDQUESTION)
    go

alter table ANSWER
    add constraint FK_ANSWER_RELATIONS_USERS foreign key (IDUSER)
        references USERS (IDUSER)
    go

alter table ANSWER
    add constraint FK_ANSWER_RELATIONS_PROCESSQ foreign key (IDQUEST)
        references PROCESSQUESTIONARY (IDQUEST)
    go

alter table ATTACHFILE
    add constraint FK_ATTACHFI_HAVE1_PROCESSM foreign key (IDPROCESS)
        references PROCESSMANAGER (IDPROCESS)
    go

alter table ATTACHFILE
    add constraint FK_ATTACHFI_HAVE2_PROCESSI foreign key (IDPOLICY)
        references PROCESSINSURANCEPOLICY (IDPOLICY)
    go

alter table ATTACHFILE
    add constraint FK_ATTACHFI_RELATIONS_ANSWER foreign key (IDANSWER)
        references ANSWER (IDANSWER)
    go

alter table ATTACHFILE
    add constraint FK_ATTACHFI_RELATIONS_PROCESSQ foreign key (IDQUEST)
        references PROCESSQUESTIONARY (IDQUEST)
    go

alter table ATTACHFILE
    add constraint FK_ATTACHFI_RELATIONS_QUESTION foreign key (IDQUESTION)
        references QUESTION (IDQUESTION)
    go

alter table OPTIONS
    add constraint FK_OPTIONS_QUESTIONX_QUESTION foreign key (IDQUESTION)
        references QUESTION (IDQUESTION)
    go

alter table PROCESSINSURANCEPOLICY
    add constraint FK_PROCESSI_HAVE_PROCESSM foreign key (IDPROCESS)
        references PROCESSMANAGER (IDPROCESS)
    go

alter table PROCESSINSURANCEPOLICY
    add constraint FK_PROCESSI_RELATIONS_INSURANC foreign key (IDINSURANCE)
        references INSURANCEPOLICY (IDINSURANCE)
    go

alter table PROCESSQUESTIONARY
    add constraint FK_PROCESSQ_RELATIONS_PROCESSI foreign key (IDPOLICY)
        references PROCESSINSURANCEPOLICY (IDPOLICY)
    go

alter table PROCESSQUESTIONARY
    add constraint FK_PROCESSQ_RELATIONS_QUESTION foreign key (IDQUESTIONNAIRE)
        references QUESTIONNAIRE (IDQUESTIONNAIRE)
    go

alter table QUESTION
    add constraint FK_QUESTION_QUESTIONN_QUESTION foreign key (IDQUESTIONNAIRE)
        references QUESTIONNAIRE (IDQUESTIONNAIRE)
    go

alter table QUESTIONNAIRE
    add constraint FK_QUESTION_RELATIONS_INSURANC foreign key (IDINSURANCE)
        references INSURANCEPOLICY (IDINSURANCE)
    go

