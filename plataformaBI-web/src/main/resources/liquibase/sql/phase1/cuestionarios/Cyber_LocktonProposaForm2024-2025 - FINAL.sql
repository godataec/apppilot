INSERT INTO QUESTIONNAIRE (IDQUESTIONNAIRE, IDINSURANCE, NAMEQUESTIONNAIRE, DESCRIPTION, CREATEDAT, STATUS, SOURCEFILE, TOTALQUESTION)
VALUES ('10','2','CYBER LOCKTON PROPOSAL FORM','CYBER LOCKTON PROPOSAL FORM','','TRUE','Cyber_LocktonProposaForm2024-2025.docx',485);

INSERT INTO QUESTION (IDQUESTION,IDQUESTIONNAIRE,TYPEQUESTION, DESCRIPTIONQUESTION ,QUESTIONTEXT ,QUESTIONJSON ,PARENTID ,DOCUMENTID) VALUES

(10001,'10','DES','I','General information','','',''),
(10002,'10','DES','','PLEASE COMPLETE EACH SECTION.','','',''),

(10003,'10','DES','','Name & address of firm','','',''),
(10004,'10','NORMAL','','Full name: ','','10003','Clocktonproposals1p1a'),
(10005,'10','NORMAL','','Address: ','','10003','Clocktonproposals1p1b'),
(10006,'10','NORMAL','','City: ','','10005','Clocktonproposals1p1c'),
(10007,'10','NORMAL','','State: ','','10005','Clocktonproposals1p1d'),
(10008,'10','NORMAL','','ZIP/postcode: ','','10005','Clocktonproposals1p1e'),
(10009,'10','NORMAL','','Website: ','','10003','Clocktonproposals1p1f'),

(10010,'10','DES','','Individual completing application form information','','',''),
(10011,'10','NORMAL','','Full name: ','','10010','Clocktonproposals1p1h'),
(10012,'10','NORMAL','','Surname: ','','10010','Clocktonproposals1p1i'),
(10013,'10','NORMAL','','Email: ','','10010','Clocktonproposals1p1j'),

(10014,'10','DES','','Applicant''s principal contact in the event of a security or privacy breach','','',''),
(10015,'10','NORMAL','','Name:','','10014','Clocktonproposals1p1k'),
(10016,'10','NORMAL','','Title:','','10014','Clocktonproposals1p1m'),
(10017,'10','NORMAL','','Email:','','10014','Clocktonproposals1p1n'),
(10018,'10','NORMAL','','Phone:','','10014','Clocktonproposals1p1o'),

(10019,'10','DES','','Type of business','','',''),
(10020,'10','OPTIONAL-NORMAL','','Select Business:','{"S1P3A":{"Id":"S1P3A","text":"","code":"Clocktonproposals1p1ar1"}}','10019',''),
(10021,'10','NORMAL','','Date established: ','','10019','Clocktonproposals1p1q'),
(10022,'10','NORMAL','','Business description: ','','10019','Clocktonproposals1p1r'),

-- ANNUAL REVENUES
(10023,'10','DES','','ANNUAL REVENUES','','',''),
(10024,'10','DES','','Healthcare applicants: Please provide net patient services revenues. All other applicants — please provide gross revenues.','','10023',''),
(10025,'10','SUB','','U.S. revenue','{"S2P1AR1":{"Id":"S2P1AR1","text":"Last complete financial year","code":"Clocktonproposals2p1ar1"},"S2P1AR2":{"Id":"S2P1AR2","text":"Current year (estimate)","code":"Clocktonproposals2p1ar2"},"S2P1AR3":{"Id":"S2P1AR3","text":"Next year (estimate)","code":"Clocktonproposals2p1ar3"}}','10023',''),
(10026,'10','SUB','','International revenue','{"S2P1BR1":{"Id":"S2P1BR1","text":"Last complete financial year","code":"Clocktonproposals2p1br1"},"S2P1BR2":{"Id":"S2P1BR2","text":"Current year (estimate)","code":"Clocktonproposals2p1br2"},"S2P1BR3":{"Id":"S2P1BR3","text":"Next year (estimate)","code":"Clocktonproposals2p1br3"}}','10023',''),
(10027,'10','SUB','','Gross profits','{"S2P1CR1":{"Id":"S2P1CR1","text":"Last complete financial year","code":"Clocktonproposals2p1cr1"},"S2P1CR2":{"Id":"S2P1CR2","text":"Current year (estimate)","code":"Clocktonproposals2p1cr2"},"S2P1CR3":{"Id":"S2P1CR3","text":"Next year (estimate)","code":"Clocktonproposals2p1cr3"}}','10023',''),

(10028,'10','OPTIONAL','','Do you generate revenues and have a presence i.e. "an establishment" in territories outside the U.S.?','{"S2P2A":{"Id":"S2P2A","text":"","code":"Clocktonproposals2p2a"}}','10023',''),

(10029,'10','DES','','If ''Yes'', please provide a breakdown by appendix to this application. Please note that revenues in Canada and Australia should be further broken down by province and state for tax purposes.','','10023',''),

(10030,'10','OPTIONAL','','Do you generate revenues and have a presence, i.e., "an establishment", in territories Inside the EEA (excluding U.K.)?','{"S2P2B":{"Id":"S2P2B","text":"","code":"Clocktonproposals2p2b"}}','10023',''),

(10031,'10','SUB','','If ''Yes'', please list the territories:','{"S2P2C":{"Id":"S2P2C","text":"","code":"Clocktonproposals2p2c"}}','10023',''),
(10032,'10','SUB','','Last complete financial year','{"S2P2DR1":{"Id":"S2P2DR1","text":"% online trading","code":"Clocktonproposals2p2dr1"},"S2P2DR2":{"Id":"S2P2DR2","text":"% business to business","code":"Clocktonproposals2p2dr2"},"S2P2DR3":{"Id":"S2P2DR3","text":"% business to consumer","code":"Clocktonproposals2p2dr3"}}','10023',''),

-- Changes to the business
(10033,'10','DES','','Changes to the business:','','',''),

(10034,'10','OPTIONAL','','Does the Applicant anticipate any changes in business activities, mergers, acquisitions, or operations during the next 12 months? If ''Yes'', please describe in an appendix to this application.','{"S2P2E":{"Id":"S2P2E","text":"","code":"Clocktonproposals2p2e"}}','10033',''),
(10035,'10','SUB','','Please describe any acquisitions, divestitures, and changes to business operations over the past 12 months.','{"S2P2F":{"Id":"S2P2F","text":"","code":"Clocktonproposals2p2f"}}','10033',''),
(10036,'10','OPTIONAL','','Are newly acquired companies required to meet certain cybersecurity standards before they are connected to the network?','{"S2P2G":{"Id":"S2P2G","text":"","code":"Clocktonproposals2p2g"}}','10033',''),
(10037,'10','OPTIONAL','','Is a cybersecurity audit part of the formal acquisition process?','{"S2P2H":{"Id":"S2P2H","text":"","code":"Clocktonproposals2p2h"}}','10033',''),

-- SECTION II: RISK ASSESSMENT
(10038,'10','DES','II','Risk assessment','','',''),
(10039,'10','DES','','Throughout this application, there are several important terms. For clarity, please use the following definitions to guide your answers.','','10038',''),
(10040,'10','DES','','•Vital Assets: Assets which are key to the organization''s success and operation. Vital assets include, but are not limited to, applications which support business production, applications which store business critical and/or sensitive data, and core technology services such as directory services, document repositories, and email.','','10039',''),
(10041,'10','DES','','•Domain Administrator: User accounts, excluding Service Accounts, which are privileged (see below). In an Active Directory environment, this would include Enterprise Admins, Domain Admins, and the (built-in domain) Administrators groups, including nested groups/accounts. In Azure, this would include Global Administrators, Hybrid Identity Administrators, and Privileged Role Administrators.','','10039',''),
(10042,'10','DES','','•Service Accounts: Accounts used for running applications and other processes. They are not typically used by humans.','','10039',''),
(10043,'10','DES','','•Privileged: Any account having administrative rights in whatever solution is in use for directory services, identity provider (IdP), rights management, etc. In an Active Directory environment, this would include Enterprise Admins, Domain Admins, and the (built-in domain) Administrators groups, including nested groups/accounts. In Azure, this would include Global Administrators, Hybrid Identity Administrators, and Privileged Role Administrators.','','10039',''),

(10044,'10','SUB','1','Annual IT budget:','{"S2P2I":{"Id":"S2P2I","text":"","code":"Clocktonproposals2p2i"}}','10038',''),
(10045,'10','SUB','2','Percentage of IT budget spent on cyber security:','{"S2P2J":{"Id":"S2P2J","text":"","code":"Clocktonproposals2p2j"}}','10038',''),
(10046,'10','SUB','3','Full-time IT employees:','{"S2P2K":{"Id":"S2P2K","text":"","code":"Clocktonproposals2p2k"}}','10038',''),
(10047,'10','SUB','4','Full-time cybersecurity employees:','{"S2P2L":{"Id":"S2P2L","text":"","code":"Clocktonproposals2p2l"}}','10038',''),

(10048,'10','DES','5','How centralized is the Applicant''s information security program? (Choose one)','','10038',''),
(10049,'10','SELECTOR','a','Information security at the Applicant is centrally managed, and the policies apply to all operations. Where exceptions are made, it''s by asset only (as opposed to by operation/legal entity).','{"S2P2M":{"Id":"S2P2M","text":"","code":"Clocktonproposals2p2m"}}','10048',''),
(10050,'10','SELECTOR','b','Information security at the Applicant is centrally managed, but exceptions are made for certain operation/legal entities. The controls as outlined below apply to greater than or equal to 98% of total endpoints.','{"S2P2N":{"Id":"S2P2N","text":"","code":"Clocktonproposals2p2n"}}','10048',''),
(10051,'10','SELECTOR','c','Information security at the Applicant is centrally managed, but exceptions are made for certain operation/legal entities. The controls as outlined below apply to less than 98% of total endpoints.','{"S2P2O":{"Id":"S2P2O","text":"","code":"Clocktonproposals2p2o"}}','10048',''),
(10052,'10','SELECTOR','d','Information security at the Applicant is federated, but the controls outlined below apply to greater than or equal to 98% of total endpoints.','{"S2P2P":{"Id":"S2P2P","text":"","code":"Clocktonproposals2p2p"}}','10048',''),
(10053,'10','SELECTOR','e','Information security at the Applicant is federated, and the controls outlined below apply to greater than 50% of total endpoints, but less than 98% of total endpoints.','{"S2P2Q":{"Id":"S2P2Q","text":"","code":"Clocktonproposals2p2q"}}','10048',''),
(10054,'10','SELECTOR','f','Information security is managed by individual legal entities or operating units. The controls below are based on a survey of all entities and operating units.','{"S2P2R":{"Id":"S2P2R","text":"","code":"Clocktonproposals2p2r"}}','10048',''),
(10055,'10','SELECTOR-NORMAL','g','Don''t know/other — Add addendum if other.','{"S2P2S":{"Id":"S2P2S","text":"","code":"Clocktonproposals2p2s"}}','10048',''),

(10056,'10','DES','6','Does the Applicant:','','10038',''),
(10057,'10','OPTIONAL','a','Have a Data Protection Officer or someone in charge of data security?','{"S2P6A":{"Id":"S2P6A","text":"","code":"Clocktonproposals2p6a"}}','10056',''),

(10058,'10','OPTIONAL','b','Administer a corporate-wide policy governing security, privacy, and acceptable use of company property for all employees and independent contractors?','{"S2P6B":{"Id":"S2P6B","text":"","code":"Clocktonproposals2p6b"}}','10056',''),
(10059,'10','OPTIONAL','i','If ''Yes'', does acceptable use policy include consequences for policy violations?','{"S2P6B1":{"Id":"S2P6B1","text":"","code":"Clocktonproposals2p6b1"}}','10058',''),
(10060,'10','OPTIONAL','ii','Are users disallowed from accessing social media platforms from organizational assets except where there is a defined business need?','{"S2P6B2":{"Id":"S2P6B2","text":"","code":"Clocktonproposals2p6b2"}}','10058',''),
(10061,'10','OPTIONAL','iii','Are users disallowed from accessing personal email from organizational assets?','{"S2P6C":{"Id":"S2P6C","text":"","code":"Clocktonproposals2p6c"}}','10058',''),
(10062,'10','OPTIONAL','iv','Are administrators explicitly disallowed from internet use and personal email from their privileged accounts?','{"S2P6D":{"Id":"S2P6D","text":"","code":"Clocktonproposals2p6d"}}','10058',''),
(10063,'10','OPTIONAL','v','Are users and administrators responsible for keeping their computers and accounts safe from common risks or issues?','{"S2P6D2":{"Id":"S2P6D2","text":"","code":"Clocktonproposals2p6d2"}}','10058',''),
(10064,'10','OPTIONAL','vi','Are users and administrator required to report suspected violations?','{"S2P6D3":{"Id":"S2P6D3","text":"","code":"Clocktonproposals2p6d3"}}','10058',''),

(10065,'10','OPTIONAL','c','Perform background checks on all employees and independent contractors with access to sensitive data?','{"S2P6E":{"Id":"S2P6E","text":"","code":"Clocktonproposals2p6e"}}','10056',''),
(10066,'10','OPTIONAL','d','Restrict user access to sensitive data/information based upon the job function of the employee or independent contractor?','{"S2P7A":{"Id":"S2P7A","text":"","code":"Clocktonproposals2p7a"}}','10056',''),
(10067,'10','OPTIONAL','i','If ''Yes'', is such access reconsidered on at least an annual basis?','{"S2P7A1":{"Id":"S2P7A1","text":"","code":"Clocktonproposals2p7a1"}}','10066',''),

(10068,'10','OPTIONAL','7','Does the Applicant use a third party or Managed Service Provider to administer their technology?','{"S2P8":{"Id":"S2P8","text":"","code":"Clocktonproposals2p8"}}','10038',''),
(10069,'10','SELECTOR-NORMAL','a','If ''Yes'', select all that are true: Applicant utilizes an MSP for:','{"S2P8A1":{"Id":"S2P8A1","text":"","code":"Clocktonproposals2p8a1"}}','10068',''),
(10070,'10','OPTIONAL','b','If ''Yes'', is the third party or Managed Service Provider given persistent access to the Applicant''s resources, not needing authorization to connect?','{"S2P8B":{"Id":"S2P8B","text":"","code":"Clocktonproposals2p8b"}}','10068',''),

(10071,'10','OPTIONAL','8','Does the Applicant have an inventory of all data stores, which includes the data owners, the asset it is stored on, sensitivity, retention limits and disposal requirements for at least all sensitive data?','{"S2P9":{"Id":"S2P9","text":"","code":"Clocktonproposals2p9"}}','10038',''),
(10072,'10','OPTIONAL','a','If ''Yes'', is it updated at least annually?','{"S2P9A":{"Id":"S2P9A","text":"","code":"Clocktonproposals2p9a"}}','10071',''),

(10073,'10','OPTIONAL','9','Has the Applicant defined and documented all vital assets?','{"S2P10":{"Id":"S2P10","text":"","code":"Clocktonproposals2p10"}}','10038',''),
(10074,'10','OPTIONAL','a','If ''Yes'', is the vital asset inventory updated at least quarterly?','{"S2P10A":{"Id":"S2P10A","text":"","code":"Clocktonproposals2p10a"}}','10073',''),

(10075,'10','OPTIONAL','10','Does the Applicant have a process to actively identify vital assets?','{"S2P11":{"Id":"S2P11","text":"","code":"Clocktonproposals2p11"}}','10038',''),

(10076,'10','OPTIONAL','11','Does the Applicant prioritize vital assets by importance to business operations?','{"S2P12":{"Id":"S2P12","text":"","code":"Clocktonproposals2p12"}}','10038',''),

(10077,'10','OPTIONAL','12','Does the Applicant have an inventory of all hardware assets, including end user devices, network devices, appliances, IoT devices, and servers?','{"S2P13":{"Id":"S2P13","text":"","code":"Clocktonproposals2p13"}}','10038',''),
(10078,'10','SELECTOR','a','If ''Yes'', does it contain:','{"S2P13A":{"Id":"S2P13A","text":"","code":"Clocktonproposals2p13a"}}','10077',''),
(10079,'10','OPTIONAL-NORMAL','b','What frequency is the inventory updated?','{"S2P13B1":{"Id":"S2P13B1","text":"Annually","code":"Clocktonproposals2p13b1"}}','10077',''),

(10080,'10','OPTIONAL','13','Does the Applicant have a process to discover hardware assets on its network?','{"S2P14":{"Id":"S2P14","text":"","code":"Clocktonproposals2p14"}}','10038',''),
(10081,'10','OPTIONAL-NORMAL','a','If ''Yes'', how frequently is process run?','{"S2P14A":{"Id":"S2P14A","text":"","code":"Clocktonproposals2p14a"}}','10080',''),

(10082,'10','OPTIONAL','14','Does the Applicant have an inventory of all licensed software?','{"S2P15":{"Id":"S2P15","text":"","code":"Clocktonproposals2p15"}}','10038',''),
(10083,'10','OPTIONAL-NORMAL','a','If ''Yes'', what frequency is the inventory updated?','{"S2P15A1":{"Id":"S2P15A1","text":"","code":"Clocktonproposals2p15a1"}}','10082',''),

(10084,'10','OPTIONAL','15','Does the Applicant have a process to decommission unused systems?','{"S2P16":{"Id":"S2P16","text":"","code":"Clocktonproposals2p16"}}','10038',''),
(10085,'10','OPTIONAL','16','Does the Applicant use on-premises Microsoft Active Directory, regardless of whether it is authoritative?','{"S2P17":{"Id":"S2P17","text":"","code":"Clocktonproposals2p17"}}','10038',''),
(10086,'10','NORMAL','17','Please state the number of servers operated by or on behalf of the Applicant:','','10038','Clocktonproposals2p18'),
(10087,'10','NORMAL','18','Please state the number of endpoints operated by or on behalf of the Applicant:','{"S2P19R1":{"Id":"S2P19R1","text":"","code":"Clocktonproposals2p19r1"},"S2P19R2":{"Id":"S2P19R2","text":"Desktops","code":"Clocktonproposals2p19r2"},"S2P19R3":{"Id":"S2P19R3","text":"Laptops Other (please specify)","code":"Clocktonproposals2p19r3"},"S2P19R4":{"Id":"S2P19R4","text":"Other (please specify)","code":"Clocktonproposals2p19r4"}}','10038',''),
(10088,'10','MULTIPLE','19','Please state the percent of critical systems hosted:','{"S2P20R1":{"Id":"S2P20R1","text":"On premises","code":"Clocktonproposals2p20r1"},"S2P20R2":{"Id":"S2P20R2","text":"In a cloud environment","code":"Clocktonproposals2p20r2"}}','10038',''),
(10089,'10','SUB','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S2P20AR1":{"Id":"S2P20AR1","text":"","code":"Clocktonproposals2p20Ar1"}}','10088',''),

-- SECTION III: DATA ASSESSMENT
(10090,'10','DES','','DATA ASSESSMENT','','',''),
(10091,'10','DES','1','Please identify nature of sensitive information stored by the Applicant:','','10090',''),

(10092,'10','OPTIONAL-NORMAL','a','Personally identifiable information','{"S3P1A":{"Id":"S3P1A","text":"","code":"Clocktonproposals3p1a"}}','10091',''),
(10093,'10','OPTIONAL-NORMAL','b','Medical records','{"S3P1B":{"Id":"S3P1B","text":"","code":"Clocktonproposals3p1b"}}','10091',''),
(10094,'10','OPTIONAL-NORMAL','c','Financial information','{"S3P1C":{"Id":"S3P1C","text":"","code":"Clocktonproposals3p1c"}}','10091',''),
(10095,'10','OPTIONAL-NORMAL','d','Driver license numbers','{"S3P1D":{"Id":"S3P1D","text":"","code":"Clocktonproposals3p1d"}}','10091',''),
(10096,'10','OPTIONAL-NORMAL','e','Social Security/National Insurance numbers','{"S3P1E":{"Id":"S3P1E","text":"","code":"Clocktonproposals3p1e"}}','10091',''),
(10097,'10','OPTIONAL-NORMAL','f','Other (please specify below)','{"S3P1F":{"Id":"S3P1F","text":"","code":"Clocktonproposals3p1f"}}','10091',''),

(10098,'10','NORMAL','2','Please estimate the total number of unique individuals for whom records are currently stored by the Applicant','','10090','Clocktonproposals3p2'),
(10099,'10','NORMAL','3','In respect of 2., to the right, please estimate the maximum number of records held within a single database:','','10090','Clocktonproposals3p3'),

(10100,'10','OPTIONAL-NORMAL','4','Does the Applicant process data for third-party companies? If ''Yes'', please estimate the total number of records processed','{"S3P4":{"Id":"S3P4","text":"","code":"Clocktonproposals3p4"}}','10090',''),

(10101,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10090','Clocktonproposals3p5'),

-- SECTION IV: EMPLOYEES
(10102,'10','DES','','EMPLOYEES','','',''),
(10103,'10','DES','','Does the Applicant:','','10102',''),

(10104,'10','OPTIONAL','1','Require users to change passwords on at least a quarterly basis?','{"S4P1":{"Id":"S4P1","text":"","code":"Clocktonproposals4p1"}}','10103',''),
(10105,'10','OPTIONAL','2','Require strong passwords for administrator rights, e.g., 10 characters using a mix of alphabetic, numeric, and other characters?','{"S4P2":{"Id":"S4P2","text":"","code":"Clocktonproposals4p2"}}','10103',''),
(10106,'10','OPTIONAL','3','Have a solution to prevent users from setting common and known-compromised passwords, even if they meet complexity requirements? (e.g. "1g2w3e4r5t" and "Passw0rd!")','{"S4P3":{"Id":"S4P3","text":"","code":"Clocktonproposals4p3"}}','10103',''),
(10107,'10','OPTIONAL','4','Enforce rotation of administrator access credentials at least every 30 days?','{"S4P4":{"Id":"S4P4","text":"","code":"Clocktonproposals4p4"}}','10103',''),
(10108,'10','OPTIONAL','5','Require all employees and independent contractors to undergo annual cybersecurity training including phishing?','{"S4P5":{"Id":"S4P5","text":"","code":"Clocktonproposals4p5"}}','10103',''),
(10109,'10','OPTIONAL','6','Terminate user access rights as part of its employee and independent contractor exit processes?','{"S4P6":{"Id":"S4P6","text":"","code":"Clocktonproposals4p6"}}','10103',''),
(10110,'10','NORMAL','7','Please confirm the total number of employees','','10103','Clocktonproposals4p7'),
(10111,'10','NORMAL','8','Please confirm the total number of computer users, if different than employee count.','{"S4P8":{"Id":"S4P8","text":"","code":"Clocktonproposals4p8"}}','10103',''),
(10112,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S4P9":{"Id":"S4P9","text":"","code":"Clocktonproposals4p9"}}','10103',''),

-- SECTION V: MULTIFACTOR AUTHENTICATION
(10113,'10','DES','','MULTIFACTOR AUTHENTICATION','','',''),
(10114,'10','DES','','Does the Applicant:','','10113',''),

(10115,'10','OPTIONAL','1','Require multifactor authentication for the following access?','{"S5P1":{"Id":"S5P1","text":"","code":"Clocktonproposals5p1"}}','10114',''),
(10116,'10','OPTIONAL','a','Critical information inside the network','{"S5P1A":{"Id":"S5P1A","text":"","code":"Clocktonproposals5p1a"}}','10115',''),
(10117,'10','OPTIONAL','b','Remote network access','{"S5P1B":{"Id":"S5P1B","text":"","code":"Clocktonproposals5p1b"}}','10115',''),
(10118,'10','OPTIONAL','i','VPN','{"S5P1B1":{"Id":"S5P1B1","text":"","code":"Clocktonproposals5p1b1"}}','10117',''),
(10119,'10','OPTIONAL','ii','VDI','{"S5P1B2":{"Id":"S5P1B2","text":"","code":"Clocktonproposals5p1b2"}}','10117',''),
(10120,'10','OPTIONAL','iii','Sensitive cloud applications','{"S5P1B3":{"Id":"S5P1B3","text":"","code":"Clocktonproposals5p1b3"}}','10117',''),
(10121,'10','OPTIONAL','iv','Sensitive web applications','{"S5P1B4":{"Id":"S5P1B4","text":"","code":"Clocktonproposals5p1b4"}}','10117',''),
(10122,'10','OPTIONAL','c','Administrator and privileged accounts','{"S5P1C":{"Id":"S5P1C","text":"","code":"Clocktonproposals5p1c"}}','10115',''),
(10123,'10','OPTIONAL','d','Personal devices when connecting with the network','{"S5P1D":{"Id":"S5P1D","text":"","code":"Clocktonproposals5p1d"}}','10115',''),
(10124,'10','OPTIONAL','e','Independent contractors and vendors accessing the network','{"S5P1E":{"Id":"S5P1E","text":"","code":"Clocktonproposals5p1e"}}','10115',''),
(10125,'10','OPTIONAL','f','Independent contractors and vendors accessing sensitive cloud or web applications','{"S5P1F":{"Id":"S5P1F","text":"","code":"Clocktonproposals5p1f"}}','10115',''),

(10126,'10','OPTIONAL','2','Allow External Remote Desktop Protocol (RDP)? If ''Yes'', are the following implemented:','{"S5P2":{"Id":"S5P2","text":"","code":"Clocktonproposals5p2"}}','10114',''),
(10127,'10','OPTIONAL','a','VPN access only ','{"S5P2A":{"Id":"S5P2A","text":"","code":"Clocktonproposals5p2a"}}','10126',''),
(10128,'10','OPTIONAL','b','Multifactor authentication for access ','{"S5P2B":{"Id":"S5P2B","text":"","code":"Clocktonproposals5p2b"}}','10126',''),
(10129,'10','OPTIONAL','c','Network level authentication enabled','{"S5P2C":{"Id":"S5P2C","text":"","code":"Clocktonproposals5p2c"}}','10126',''),
(10130,'10','OPTIONAL','d','RDP honeypot(s)','{"S5P2D":{"Id":"S5P2D","text":"","code":"Clocktonproposals5p2d"}}','10126',''),
(10131,'10','SUB','e','Other (Please identify)','{"S5P2E":{"Id":"S5P2E","text":"","code":"Clocktonproposals5p2e"}}','10126',''),

(10132,'10','SELECTOR-NORMAL','3','Confirm the type(s) of MFA in place:','{"S5P3R1":{"Id":"S5P3R1","text":"","code":"Clocktonproposals5p3r1"}}','10114',''),
(10133,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10114','Clocktonproposals5p3ra'),

-- SECTION VI: PRIVILEGED ACCESS MANAGEMENT
(10134,'10','DES','','PRIVILEGED ACCESS MANAGEMENT','','',''),
(10135,'10','DES','','Does the Applicant:','','10134',''),

(10136,'10','OPTIONAL','1','Manage privileged accounts using tooling (e.g., CyberArk, PAM)?','{"S6P1":{"Id":"S6P1","text":"","code":"Clocktonproposals6p1"}}','10135',''),
(10137,'10','OPTIONAL','2','Enroll any of the following accounts into a PAM tool?','{"S6P2":{"Id":"S6P2","text":"","code":"Clocktonproposals6p2"}}','10135',''),
(10138,'10','SELECTOR-NORMAL','','','{"S6P2R1":{"Id":"S6P2R1","text":"","code":"Clocktonproposals6p2r1"}}','10137',''),

(10139,'10','DES','','If ''No'', please provide additional information for any local administrative accounts that are not enrolled into the PAM tool:','','10135',''),
(10140,'10','OPTIONAL','a','Please confirm that identical local admin credentials are not used (i.e., there is not a common username and password used for each local admin accounts).','{"S6P2A":{"Id":"S6P2A","text":"","code":"Clocktonproposals6p2a"}}','10139',''),
(10141,'10','SUB','b','Please provide details below on how unauthorized local admin privilege escalation on workstation is detected:','{"S6P2B":{"Id":"S6P2B","text":"","code":"Clocktonproposals6p2b"}}','10139',''),
(10142,'10','SUB-OPTIONAL','c','Have you implemented Microsoft''s Local Administrator Password Solution (LAPS)?','{"S6P2C":{"Id":"S6P2C","text":"","code":"Clocktonproposals6p2c"}}','10139',''),

(10143,'10','DES','3','Enabled the following features on the PAM tool:','','10135',''),

(10144,'10','OPTIONAL','a','Please confirm that identical local admin credentials are not used (i.e., there is not a common username and password used for each local admin accounts)','{"S6P3A":{"Id":"S6P3A","text":"","code":"Clocktonproposals6p3a"}}','10143',''),
(10145,'10','OPTIONAL-NORMAL','b','Credential time-out (please state time after which account locks):','{"S6P3B":{"Id":"S6P3B","text":"","code":"Clocktonproposals6p3b"}}','10143',''),
(10146,'10','OPTIONAL','c','One-time passwords:','{"S6P3C":{"Id":"S6P3C","text":"","code":"Clocktonproposals6p3c"}}','10143',''),
(10147,'10','OPTIONAL','d','Credential rotation','{"S6P3D":{"Id":"S6P3D","text":"","code":"Clocktonproposals6p3d"}}','10143',''),
(10148,'10','OPTIONAL','e','MFA','{"S6P3E":{"Id":"S6P3E","text":"","code":"Clocktonproposals6p3e"}}','10143',''),
(10149,'10','OPTIONAL','f','Real-time monitoring of account activity/detection of suspicious activity','{"S6P3F":{"Id":"S6P3F","text":"","code":"Clocktonproposals6p3f"}}','10143',''),

(10150,'10','NORMAL','4','How often are all privileged accounts (such as those used in Active Directory and SaaS solutions as well as Service and Local accounts) inventoried and reviewed? (If less than annually or not inventoried and refreshed, please provide explanation).','','10135','Clocktonproposals6p4'),

(10151,'10','OPTIONAL','5','Is logging and alerting configured for privileged account usage/changes?','{"S6P5":{"Id":"S6P5","text":"","code":"Clocktonproposals6p5"}}','10135',''),
(10152,'10','OPTIONAL','6','Are domain administrator accounts unique, separate accounts from other accounts used for everyday activities?','{"S6P6":{"Id":"S6P6","text":"","code":"Clocktonproposals6p6"}}','10135',''),

(10153,'10','OPTIONAL','7','Can Domain Administrator accounts can only be used from Privileged Access Workstations (which do not have access to internet or email?','{"S6P7":{"Id":"S6P7","text":"","code":"Clocktonproposals6p7"}}','10135',''),
(10154,'10','OPTIONAL','8','Is there a log of all actions by Domain Administrator accounts for at least the past thirty days?','{"S6P8":{"Id":"S6P8","text":"","code":"Clocktonproposals6p8"}}','10135',''),
(10155,'10','NORMAL','9','Please provide a count of the Domain Administrator accounts','{"S6P9":{"Id":"S6P9","text":"","code":"Clocktonproposals6p9"}}','10135',''),

-- SECTION VII: LOCAL ADMINISTRATIVE & SERVICE ACCOUNTS
(10156,'10','DES','','LOCAL ADMINISTRATIVE & SERVICE ACCOUNTS','','',''),
(10157,'10','DES','','Does the Applicant:','','10156',''),

(10158,'10','DES','1','Prohibit workstations from local admin rights:','','10157',''),
(10159,'10','OPTIONAL','a','All of the time?','{"S7P1A":{"Id":"S7P1A","text":"","code":"Clocktonproposals7p1a"}}','10158',''),
(10160,'10','OPTIONAL','b','Case by case?','{"S7P1B":{"Id":"S7P1B","text":"","code":"Clocktonproposals7p1b"}}','10158',''),

(10161,'10','OPTIONAL','2','Have an inventory of all privileged service accounts?','{"S7P2":{"Id":"S7P2","text":"","code":"Clocktonproposals7p2"}}','10157',''),
(10162,'10','OPTIONAL-NORMAL','','If ''Yes'', how frequently is it reviewed and updated?','{"S7P2R1":{"Id":"S7P2R1","text":"Annually","code":"Clocktonproposals7p2r1"}}','10161',''),

(10163,'10','NORMAL','3','Please provide number of privileged service accounts: ','','10157','Clocktonproposals7p3r1'),
(10164,'10','DES','a','For each privileged service account included above, please use the table provided in Supplement C of application. ','','10163',''),

(10165,'10','OPTIONAL','4','Configure service accounts using the principle of least privilege? ','{"S7P4":{"Id":"S7P4","text":"","code":"Clocktonproposals7p4"}}','10157',''),
(10166,'10','OPTIONAL','a','Are service accounts tiered such that different accounts are used to interact with workstations, servers, and authentication servers, even for the same service? ','{"S7P4A":{"Id":"S7P4A","text":"","code":"Clocktonproposals7p4a"}}','10165',''),

(10167,'10','OPTIONAL-NORMAL','5','Configure service accounts to deny any interactive logon?, If ''Yes'', please confirm the percentage: ','{"S7P5":{"Id":"S7P5","text":"","code":"Clocktonproposals7p5"}}','10157',''),
(10168,'10','OPTIONAL','6','Have specific monitoring rules in place for service accounts to alert for any abnormal behavior? ','{"S7P6":{"Id":"S7P6","text":"","code":"Clocktonproposals7p6"}}','10157',''),
(10169,'10','OPTIONAL','7','Require service account passwords to be ≥ 25 characters? ','{"S7P7":{"Id":"S7P7","text":"","code":"Clocktonproposals7p7"}}','10157',''),
(10170,'10','OPTIONAL','8','Require service account passwords to be rotated on a regular basis? ','{"S7P8":{"Id":"S7P8","text":"","code":"Clocktonproposals7p8"}}','10157',''),
(10171,'10','OPTIONAL-NORMAL','','If ''Yes'', how frequently? ','{"S7P8R1":{"Id":"S7P8R1","text":"","code":"Clocktonproposals7p8r1"}}','10170',''),

(10172,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below: ','','10157','Clocktonproposals7p8a'),

-- SECTION VIII: NETWORK OVERVIEW
(10173,'10','DES','','NETWORK OVERVIEW ','','',''),
(10174,'10','DES','','Does the Applicant: ','','10173',''),

(10175,'10','OPTIONAL-NORMAL','1','Intrusion Detection Solution (IDS)? Product name: ','','10174','Clocktonproposals8p1'),
(10176,'10','OPTIONAL-NORMAL','2','Intrusion Prevention Solution (IPS)? Product name:  ','','10174','Clocktonproposals8p2'),

(10177,'10','OPTIONAL-NORMAL','3','Endpoint Protection Platform (EPP)? Product name:  ','','10174','Clocktonproposals8p3'),
(10178,'10','SELECTOR','a','Does this include  ','{"S8P3AR1":{"Id":"S8P3AR1","text":"","code":"Clocktonproposals8p3ar1"}}','10177',''),
(10179,'10','OPTIONAL','b','Do capabilities include isolation and containment? Servers ','{"S8P3B":{"Id":"S8P3B","text":"","code":"Clocktonproposals8p3b"}}','10177',''),
(10180,'10','OPTIONAL','c','Do capabilities include behavioral detection and exploit mitigation?','{"S8P3C":{"Id":"S8P3C","text":"","code":"Clocktonproposals8p3c"}}','10177',''),

(10181,'10','OPTIONAL-NORMAL','4','Endpoint Detection and Response (EDR)? Product name:','','10174','Clocktonproposals8p4'),
(10182,'10','SUB','a','What % of Endpoints are protected by above?','{"S8P4A":{"Id":"S8P4A","text":"","code":"Clocktonproposals8p4a"}}','10181',''),
(10183,'10','SUB','b','What % of Servers are protected by above?','{"S8P4B":{"Id":"S8P4B","text":"","code":"Clocktonproposals8p4b"}}','10181',''),

(10184,'10','OPTIONAL-NORMAL','5','Managed Detection and Response (MDR)? Product name:','','10174','Clocktonproposals8p5'),
(10185,'10','OPTIONAL-NORMAL','6','Network Detection and Response (NDR)? Product name:','','10174','Clocktonproposals8p6'),

(10186,'10','OPTIONAL-NORMAL','7','Security Information and Event Management (SIEM)? Product name:','','10174','Clocktonproposals8p7'),
(10187,'10','OPTIONAL','a','If using Active Directory, are domain controller logs ingested by the SIEM?','{"S8P7A":{"Id":"S8P7A","text":"","code":"Clocktonproposals8p7a"}}','10186',''),
(10188,'10','NORMAL','b','What information does the SIEM ingest?','{"S8P7B":{"Id":"S8P7B","text":"","code":"Clocktonproposals8p7b"}}','10186',''),
(10189,'10','NORMAL','c','What percentage of Applicant''s "Vital Assets" are ingested by SIEM','{"S8P7C":{"Id":"S8P7C","text":"","code":"Clocktonproposals8p7c"}}','10186',''),
(10190,'10','NORMAL','d','How long does SIEM retain logs?','{"S8P7D":{"Id":"S8P7D","text":"","code":"Clocktonproposals8p7d"}}','10186',''),

(10191,'10','OPTIONAL-NORMAL','8','Data Loss Prevention solution (DLP) in place? Product name:','{"S8P8":{"Id":"S8P8","text":"","code":"Clocktonproposals8p8"}}','10174',''),
(10192,'10','OPTIONAL','a','Do alerts from the DLP feed into the SIEM?','{"S8P8A":{"Id":"S8P8A","text":"","code":"Clocktonproposals8p8a"}}','10191',''),
(10193,'10','OPTIONAL','b','Is your DLP solution email or network based?','{"S8P8B":{"Id":"S8P8B","text":"","code":"Clocktonproposals8p8b"}}','10191',''),
(10194,'10','OPTIONAL','c','Is your DLP solution in blocking mode?','{"S8P8C":{"Id":"S8P8C","text":"","code":"Clocktonproposals8p8c"}}','10191',''),

(10195,'10','OPTIONAL','9','Security Operations Center (SOC)? If ''Yes'',','{"S8P9":{"Id":"S8P9","text":"","code":"Clocktonproposals8p9"}}','10174',''),
(10196,'10','OPTIONAL','a','24x7 live coverage with eyes on glass?','{"S8P9A":{"Id":"S8P9A","text":"","code":"Clocktonproposals8p9a"}}','10195',''),
(10197,'10','OPTIONAL','b','Internally staffed?','{"S8P9B":{"Id":"S8P9B","text":"","code":"Clocktonproposals8p9b"}}','10195',''),
(10198,'10','OPTIONAL','c','Managed by a third party?','{"S8P9C":{"Id":"S8P9C","text":"","code":"Clocktonproposals8p9c"}}','10195',''),
(10199,'10','OPTIONAL','d','Does the SOC have authority and ability to remediate security events?','{"S8P9D":{"Id":"S8P9D","text":"","code":"Clocktonproposals8p9d"}}','10195',''),
(10200,'10','OPTIONAL','e','Is the SOC provided an updated list of vital assets at least quarterly?','{"S8P9E":{"Id":"S8P9E","text":"","code":"Clocktonproposals8p9e"}}','10195',''),
(10201,'10','SELECTOR','f','Of the products referenced in questions 1-8 of this section, which are monitored by the SOC?','{"S8P9FR1":{"Id":"S8P9FR1","text":"","code":"Clocktonproposals8p9fr1"}}','10195',''),

(10202,'10','OPTIONAL','10','Regarding the products referenced in questions 1-8 of this section, are all that require updated definitions done at least daily?','{"S8P10":{"Id":"S8P10","text":"","code":"Clocktonproposals8p10"}}','10174',''),
(10203,'10','OPTIONAL','11','Regarding the products referenced in questions 1-8 of this section, are all available anti-tamper features enabled?','{"S8P11":{"Id":"S8P11","text":"","code":"Clocktonproposals8p11"}}','10174',''),
(10204,'10','OPTIONAL','12','Regarding the products referenced in questions 1-8 of this section, are all tools set to block suspected malicious activity vs. just notify?','{"S8P12":{"Id":"S8P12","text":"","code":"Clocktonproposals8p12"}}','10174',''),

(10205,'10','OPTIONAL','13','If the Applicant is using Active Directory, which of the following Audit Policies are enabled on Domain Controllers?','{"S8P13":{"Id":"S8P13","text":"","code":"Clocktonproposals8p13"}}','10174',''),
(10206,'10','OPTIONAL','a','Audit Credential Validation (Failure)','{"S8P13A":{"Id":"S8P13A","text":"","code":"Clocktonproposals8p13a"}}','10205',''),
(10207,'10','OPTIONAL','b','Audit Process Creation (Success)','{"S8P13B":{"Id":"S8P13B","text":"","code":"Clocktonproposals8p13b"}}','10205',''),
(10208,'10','OPTIONAL','c','Audit Security Group Management (Success and Failure)','{"S8P13C":{"Id":"S8P13C","text":"","code":"Clocktonproposals8p13c"}}','10205',''),
(10209,'10','OPTIONAL','d','Audit User Account Management (Success and Failure)','{"S8P13D":{"Id":"S8P13D","text":"","code":"Clocktonproposals8p13d"}}','10205',''),
(10210,'10','OPTIONAL','e','Audit Other Account Management Events (Success and Failure)','{"S8P13E":{"Id":"S8P13E","text":"","code":"Clocktonproposals8p13e"}}','10205',''),
(10211,'10','OPTIONAL','f','Audit Sensitive Privilege Use (Success and Failure)','{"S8P13F":{"Id":"S8P13F","text":"","code":"Clocktonproposals8p13f"}}','10205',''),
(10212,'10','OPTIONAL','g','Audit Logon (Success and Failure)','{"S8P13G":{"Id":"S8P13G","text":"","code":"Clocktonproposals8p13g"}}','10205',''),
(10213,'10','OPTIONAL','h','Audit Special Logon (Success)','{"S8P13H":{"Id":"S8P13H","text":"","code":"Clocktonproposals8p13h"}}','10205',''),

(10214,'10','OPTIONAL','14','Implement a hardened baseline configuration materially rolled out across severs, laptops, desktops, and managed mobile device?','{"S8P14":{"Id":"S8P14","text":"","code":"Clocktonproposals8p14"}}','10174',''),

(10215,'10','OPTIONAL','15','Employ vulnerability scanning across your enterprise?','{"S8P15":{"Id":"S8P15","text":"","code":"Clocktonproposals8p15"}}','10174',''),
(10216,'10','SUB','a','What % of the enterprise is covered?','{"S8P15A":{"Id":"S8P15A","text":"","code":"Clocktonproposals8p15a"}}','10215',''),
(10217,'10','OPTIONAL','b','What is the frequency of scanning?','{"S8P15BR1":{"Id":"S8P15BR1","text":"","code":"Clocktonproposals8p15br1"}}','10215',''),

(10218,'10','OPTIONAL','16','Route all outbound web requests through a web proxy which monitors for and blocks potentially malicious content?','{"S8P16":{"Id":"S8P16","text":"","code":"Clocktonproposals8p16"}}','10174',''),
(10219,'10','OPTIONAL','17','Implement PowerShell best practices as outlined in the Environment Recommendations by Microsoft? ','{"S8P17":{"Id":"S8P17","text":"","code":"Clocktonproposals8p17"}}','10174',''),
(10220,'10','OPTIONAL-NORMAL','18','Segment your network based on certain criteria? ','{"S8P18R1":{"Id":"S8P18R1","text":"","code":"Clocktonproposals8p18r1"}}','10174',''),
(10221,'10','OPTIONAL','19','Do you segregate operational technology from information technology networks? ','{"S8P19":{"Id":"S8P19","text":"","code":"Clocktonproposals8p19"}}','10174',''),
(10222,'10','OPTIONAL','20','Configured host-based and network firewalls to disallow inbound connections by default? ','{"S8P20":{"Id":"S8P20","text":"","code":"Clocktonproposals8p20"}}','10174',''),
(10223,'10','OPTIONAL','21','An inventory of externally exposed assets? ','{"S8P21":{"Id":"S8P21","text":"","code":"Clocktonproposals8p21"}}','10174',''),
(10224,'10','OPTIONAL','22','Vulnerability scans of externally exposed assets? ','{"S8P22":{"Id":"S8P22","text":"","code":"Clocktonproposals8p22"}}','10174',''),
(10225,'10','OPTIONAL','','If ''Yes'', what is the frequency?','{"S8P22R1":{"Id":"S8P22R1","text":"","code":"Clocktonproposals8p22r1"}}','10224',''),

(10226,'10','OPTIONAL','23','Are Web Application Firewalls (WAF) in place for everything that is externally facing?','{"S8P23":{"Id":"S8P23","text":"","code":"Clocktonproposals8p23"}}','10174',''),
(10227,'10','OPTIONAL','a','If ''Yes'', is the WAF in blocking mode?','{"S8P23A":{"Id":"S8P23A","text":"","code":"Clocktonproposals8p23a"}}','10226',''),

(10228,'10','OPTIONAL','24','Protective DNS service (e,g., Quad9, OpenDNS or the public sector PDNS)?','{"S8P24":{"Id":"S8P24","text":"","code":"Clocktonproposals8p24"}}','10174',''),
(10229,'10','OPTIONAL','25','On externally exposed systems, disable or block those ports, services, and protocols known to allow the spread of ransomware? These include, but are not limited to RDP, SMBv1, SMBv2','{"S8P25":{"Id":"S8P25","text":"","code":"Clocktonproposals8p25"}}','10174',''),

(10230,'10','OPTIONAL','26','Penetration testing done by a third party?','{"S8P26":{"Id":"S8P26","text":"","code":"Clocktonproposals8p26"}}','10174',''),
(10231,'10','OPTIONAL','a','If ''Yes'', does the testing simulate known threat actor tactics, techniques, and procedures?','{"S8P26A":{"Id":"S8P26A","text":"","code":"Clocktonproposals8p26a"}}','10230',''),
(10232,'10','OPTIONAL-NORMAL','','If ''Yes'', what is the frequency?','{"S8P26AR1":{"Id":"S8P26AR1","text":"","code":"Clocktonproposals8p26ar1"}}','10230',''),
(10233,'10','SUB','','If the Applicant has any further comments on questions in the section above, please elaborate below','{"S8P26A1":{"Id":"S8P26A1","text":"","code":"Clocktonproposals8p26a1"}}','10174',''),

-- SECTION IX: EMAIL
(10234,'10','DES','','EMAIL','','',''),

(10235,'10','OPTIONAL-NORMAL','1','What email platform is in use?','{"S9P1R1":{"Id":"S9P1R1","text":"","code":"Clocktonproposals9p1r1"}}','10234',''),
(10236,'10','OPTIONAL','2','Multifactor Authentication (MFA) enabled on all user accounts?','{"S9P2":{"Id":"S9P2","text":"","code":"Clocktonproposals9p2"}}','10234',''),
(10237,'10','OPTIONAL','3','Utilize an email monitoring/filtering solution (i.e. Microsoft ATP, Proofpoint, Mimecast)?','{"S9P3":{"Id":"S9P3","text":"","code":"Clocktonproposals9p3"}}','10234',''),
(10238,'10','SUB','','If ''Yes'', enter solution.','{"S9P3R1":{"Id":"S9P3R1","text":"","code":"Clocktonproposals9p3r1"}}','10237',''),

(10239,'10','DES','4','If ''Yes'', does email monitoring/filtering solution perform any of the following?','','10234',''),
(10240,'10','OPTIONAL','a','Blocks known malicious links, attachments, and suspicious file types, including executables','{"S9P4A":{"Id":"S9P4A","text":"","code":"Clocktonproposals9p4a"}}','10239',''),
(10241,'10','OPTIONAL','b','Blocks suspicious messages based on their content or attributes of the sender','{"S9P4B":{"Id":"S9P4B","text":"","code":"Clocktonproposals9p4b"}}','10239',''),
(10242,'10','OPTIONAL','c','Has the capability to run suspicious attachments in a sandbox','{"S9P4C":{"Id":"S9P4C","text":"","code":"Clocktonproposals9p4c"}}','10239',''),

(10243,'10','OPTIONAL','5','Implemented the following to protect against phishing messages:','{"S9P5":{"Id":"S9P5","text":"","code":"Clocktonproposals9p5"}}','10234',''),
(10244,'10','OPTIONAL','','','{"S9P5R1":{"Id":"S9P5R1","text":"","code":"Clocktonproposals9p5r1"}}','10243',''),

(10245,'10','OPTIONAL','6','Conduct regular phishing simulations of staff? If so, how often:','{"S9P6":{"Id":"S9P6","text":"","code":"Clocktonproposals9p6"}}','10234',''),
(10246,'10','OPTIONAL','','','{"S9P6R1":{"Id":"S9P6R1","text":"","code":"Clocktonproposals9p6r1"}}','10245',''),

(10247,'10','OPTIONAL','7','Measure-click through/fail rate? If Yes, please confirm:','{"S9P7":{"Id":"S9P7","text":"","code":"Clocktonproposals9p7"}}','10234',''),
(10248,'10','OPTIONAL','','','{"S9P7R1":{"Id":"S9P7R1","text":"","code":"Clocktonproposals9p7r1"}}','10247',''),

(10249,'10','OPTIONAL','8','Is immediate additional training assigned for staff that fail phishing simulations?','{"S9P8":{"Id":"S9P8","text":"","code":"Clocktonproposals9p8"}}','10234',''),

(10250,'10','OPTIONAL','9','Is access to web-based email such as Outlook Web Access permitted?','{"S9P9":{"Id":"S9P9","text":"","code":"Clocktonproposals9p9"}}','10234',''),
(10251,'10','OPTIONAL','','If ''Yes'', is MFA enforced','{"S9P9A":{"Id":"S9P9A","text":"","code":"Clocktonproposals9p9a"}}','10250',''),

(10252,'10','OPTIONAL','10','Tag external emails to alert employees that the message originated from outside the organization?','{"S9P10":{"Id":"S9P10","text":"","code":"Clocktonproposals9p10"}}','10234',''),

(10253,'10','OPTIONAL','11','Filter/scan incoming emails for malicious attachments and/or links?','{"S9P11":{"Id":"S9P11","text":"","code":"Clocktonproposals9p11"}}','10234',''),
(10254,'10','OPTIONAL','','If ''Yes'', do you have the ability to automatically quarantine, detonate, and evaluate attachments?','{"S9P11A":{"Id":"S9P11A","text":"","code":"Clocktonproposals9p11a"}}','10253',''),

(10255,'10','OPTIONAL','12','Disable macros in office productivity software by default? (e.g., Microsoft Office, Google Workspace)','{"S9P12":{"Id":"S9P12","text":"","code":"Clocktonproposals9p12"}}','10234',''),
(10256,'10','OPTIONAL','','If ''Yes'', are users allowed to enable macros?','{"S9P12A":{"Id":"S9P12A","text":"","code":"Clocktonproposals9p12a"}}','10255',''),

(10257,'10','SELECTOR','13','Which legacy email protocols have been disabled? ','{"S9P13R1":{"Id":"S9P13R1","text":"","code":"Clocktonproposals9p13r1"}}','10234',''),
(10258,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below: ','{"S9P13A":{"Id":"S9P13A","text":"","code":"Clocktonproposals9p13A"}}','10234',''),

-- SECTION X: PATCHING & SOFTWARE
(10259,'10','DES','','PATCHING & SOFTWARE','','',''),

(10260,'10','OPTIONAL','1','Have a patching policy in place to install critical and high severity patches across the enterprise? If so, please','{"S10P1":{"Id":"S10P1","text":"","code":"Clocktonproposals10p1"}}','10259',''),
(10261,'10','OPTIONAL','','confirm the time frame:','{"S10P1R1":{"Id":"S10P1R1","text":"","code":"Clocktonproposals10p1r1"}}','10260',''),
(10262,'10','OPTIONAL','a','Which systems are patched?','{"S10P1AR1":{"Id":"S10P1AR1","text":"","code":"Clocktonproposals10p1ar1"}}','10260',''),
(10263,'10','OPTIONAL','b','Is compliance with the policy tracked?','{"S10P1B":{"Id":"S10P1B","text":"","code":"Clocktonproposals10p1b"}}','10260',''),
(10264,'10','OPTIONAL','i','If ''Yes'', what is the compliance rate?','{"S10P1BR1":{"Id":"S10P1BR1","text":"","code":"Clocktonproposals10p1br1"}}','10263',''),

(10265,'10','OPTIONAL','2','Have a patching policy in place to install normal severity patches across the enterprise?','{"S10P2":{"Id":"S10P2","text":"","code":"Clocktonproposals10p2"}}','10259',''),
(10266,'10','OPTIONAL','','If so, please confirm the time frame','{"S10P2R1":{"Id":"S10P2R1","text":"","code":"Clocktonproposals10p2r1"}}','10265',''),
(10267,'10','OPTIONAL','a','Which systems are patched?','{"S10P2AR1":{"Id":"S10P2AR1","text":"","code":"Clocktonproposals10p2ar1"}}','10265',''),

(10268,'10','OPTIONAL','3','Operate any end of life or end of support software or platforms?','{"S10P3":{"I d":"S10P3","text":"","code":"Clocktonproposals10p3"}}','10259',''),
(10269,'10','OPTIONAL','a','If ''Yes'', is it segregated from the rest of the network?','{"S10P3A":{"Id":"S10P3A","text":"","code":"Clocktonproposals10p3a"}}','10268',''),
(10270,'10','OPTIONAL','b','If ''Yes'', is sensitive PII data stored or processed on these assets?','{"S10P3B":{"Id":"S10P3B","text":"","code":"Clocktonproposals10p3b"}}','10268',''),
(10271,'10','OPTIONAL','c','If ''Yes'', do you purchase additional support for the software, where available?','{"S10P3C":{"Id":"S10P3C","text":"","code":"Clocktonproposals10p3c"}}','10268',''),
(10272,'10','SUB','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S10P3C1":{"Id":"S10P3C1","text":"","code":"Clocktonproposals10p3c1"}}','10259',''),

-- SECTION XI: KNOWN VULNERABILITIES
(10273,'10','DES','','KNOWN VULNERABILITIES','','',''),

(10274,'10','OPTIONAL','1','Has the Applicant been affected by any known vulnerabilities rated 10 or above in the common vulnerabilities and exposures database (https://nvd.nist.gov/general/nvd-dashboard)? (e.g. Keseya, Log4J, SolarWinds? )','{"S11P1":{"Id":"S11P1","text":"","code":"Clocktonproposals11p1"}}','10273',''),
(10275,'10','SUB','a','If ''Yes'', please outline any and all patching procedures, mitigating controls, investigations, or evidence of malicious activity below, or provide in an appendix','{"S11P1A":{"Id":"S11P1A","text":"","code":"Clocktonproposals11p1a"}}','10274',''),

-- SECTION XII: BACKUPS & RECOVERY TIME
(10276,'10','DES','','BACKUPS & RECOVERY TIME','','',''),
(10277,'10','DES','','Does the Applicant:','','10276',''),

(10278,'10','OPTIONAL','1','Conduct regular backup of data?','{"S12P1":{"Id":"S12P1","text":"","code":"Clocktonproposals12p1"}}','10277',''),
(10279,'10','OPTIONAL','2','Frequently backup critical information? At least:','{"S12P2R1":{"Id":"S12P2R1","text":"","code":"Clocktonproposals12p2r1"}}','10277',''),
(10280,'10','OPTIONAL','3','Utilize physical backup tapes?','{"S12P3":{"Id":"S12P3","text":"","code":"Clocktonproposals12p3"}}','10277',''),
(10281,'10','OPTIONAL','4','Store backups? Select all that apply:','{"S12P4R1":{"Id":"S12P4R1","text":"","code":"Clocktonproposals12p4r1"}}','10277',''),

(10282,'10','DES','5','If "Cloud" was selected in Question 4:','','10277',''),

(10283,'10','OPTIONAL','a','Is your cloud-based backup service a "syncing service"? (e.g., DropBox, OneDrive, SharePoint, Google Drive)','{"S12P5a":{"Id":"S12P5a","text":"","code":"Clocktonproposals12p5a"}}','10282',''),
(10284,'10','OPTIONAL','b','Have you determined how long it would take to restore all data from the cloud?','{"S12P5b":{"Id":"S12P5b","text":"","code":"Clocktonproposals12p5b"}}','10282',''),
(10285,'10','OPTIONAL','c','Is access to cloud backups logged with alerts configured for suspicious activity?','{"S12P5C":{"Id":"S12P5C","text":"","code":"Clocktonproposals12p5c"}}','10282',''),
(10286,'10','OPTIONAL','d','Do you utilize versioning, data deletion prevention, and/or copies of the backups in other availability zones?','{"S12P5D":{"Id":"S12P5D","text":"","code":"Clocktonproposals12p5d"}}','10282',''),

(10287,'10','OPTIONAL-NORMAL','6','If "Offline storage" was selected in Question 4, is this done at least:','{"S12P6R1":{"Id":"S12P6R1","text":"","code":"Clocktonproposals12p6r1"}}','10277',''),
(10288,'10','OPTIONAL','7','If "Off-site storage" was selected in Question 4, is this done at least: ','{"S12P7R1":{"Id":"S12P7R1","text":"","code":"Clocktonproposals12p7r1"}}','10277',''),

(10289,'10','OPTIONAL','8','Subject backups to the following measures? Select all that apply: ','{"S12P8R1":{"Id":"S12P8R1","text":"","code":"Clocktonproposals12p8r1"}}','10277',''),
(10290,'10','OPTIONAL','','If "Encryption" was selected in Question 8, is there an offline backup of encryption keys? ','{"S12P8A":{"Id":"S12P8A","text":"","code":"Clocktonproposals12p8a"}}','10289',''),

(10291,'10','OPTIONAL','9','Store unique backup credentials separately from other user credentials? ','{"S12P9":{"Id":"S12P9","text":"","code":"Clocktonproposals12p9"}}','10277',''),
(10292,'10','OPTIONAL-NORMAL','10','Employ a physical and logical separation of backups from the rest of the network?, If ''No'', please outline the backup storage procedure: ','{"S12P10":{"Id":"S12P10","text":"","code":"Clocktonproposals12p10"}}','10277',''),
(10293,'10','OPTIONAL','11','Use unique accounts (not used for other systems) to access backups? ','{"S12P11":{"Id":"S12P11","text":"","code":"Clocktonproposals12p11"}}','10277',''),
(10294,'10','OPTIONAL','12','Use accounts that are domain joined to access backups? ','{"S12P12":{"Id":"S12P12","text":"","code":"Clocktonproposals12p12"}}','10277',''),

(10295,'10','OPTIONAL','13','Test a full recovery from a backup?  ','{"S12P13":{"Id":"S12P13","text":"","code":"Clocktonproposals12p13"}}','10277',''),
(10296,'10','OPTIONAL-NORMAL','','If yes, the frequency of testing is at least:','{"S12P13AR1":{"Id":"S12P13AR1","text":"","code":"Clocktonproposals12p13ar1"}}','10295',''),

(10297,'10','OPTIONAL','14','Test the integrity of backups prior to restoration to be confident it is free from malware? ','{"S12P14":{"Id":"S12P14","text":"","code":"Clocktonproposals12p14"}}','10277',''),
(10298,'10','OPTIONAL','15','Maintain a warm or hot backup site for the purposes of resiliency, continuity, or redundancy?','{"S12P15":{"Id":"S12P15","text":"","code":"Clocktonproposals12p15"}}','10277',''),
(10299,'10','OPTIONAL-NORMAL','16','What is the Applicant''s average time to triage and contain security incidents of workstations year to date?','{"S12P16R1":{"Id":"S12P16R1","text":"","code":"Clocktonproposals12p16r1"}}','10277',''),
(10300,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S12P16A":{"Id":"S12P16A","text":"","code":"Clocktonproposals12p16a"}}','10277',''),

-- SECTION XIII: NETWORK SECURITY ASSESSMENT
(10301,'10','DES','','NETWORK SECURITY ASSESSMENT','','',''),
(10302,'10','DES','','Does the Applicant:','','10301',''),

(10303,'10','OPTIONAL','1','Conduct security policy and procedure audits and remediate critical deficiencies?','{"S13P1":{"Id":"S13P1","text":"","code":"Clocktonproposals13p1"}}','10302',''),
(10304,'10','OPTIONAL','2','Have physical security to control access to its data centers/server rooms? (e.g. 24 hr. guards, access cards, biometric access)','{"S13P2":{"Id":"S13P2","text":"","code":"Clocktonproposals13p2"}}','10302',''),
(10305,'10','OPTIONAL','3','Replace factory default settings when configuring software and systems?','{"S13P3":{"Id":"S13P3","text":"","code":"Clocktonproposals13p3"}}','10302',''),
(10306,'10','OPTIONAL','4','Enforce a clear desk policy at all sites?','{"S13P4":{"Id":"S13P4","text":"","code":"Clocktonproposals13p4"}}','10302',''),
(10307,'10','OPTIONAL','5','Have an enterprise-wide data retention and destruction policy?','{"S13P5":{"Id":"S13P5","text":"","code":"Clocktonproposals13p5"}}','10302',''),
(10308,'10','OPTIONAL','','If ''Yes'', is this policy regularly reviewed and updated?','{"S13P5A":{"Id":"S13P5A","text":"","code":"Clocktonproposals13p5a"}}','10307',''),
(10309,'10','OPTIONAL','6','Have antivirus protection in place and is it updated frequently?','{"S13P6":{"Id":"S13P6","text":"","code":"Clocktonproposals13p6"}}','10302',''),
(10310,'10','OPTIONAL','7','Review antivirus software and firewalls, configurations, and settings on at least a quarterly basis?','{"S13P7":{"Id":"S13P7","text":"","code":"Clocktonproposals13p7"}}','10302',''),

(10311,'10','OPTIONAL','8','Build information security measures into software that is developed or modified by internal resources?','{"S13P8":{"Id":"S13P8","text":"","code":"Clocktonproposals13p8"}}','10302',''),
(10312,'10','OPTIONAL','9','Require all connecting devices to have antivirus and firewall installed?','{"S13P9":{"Id":"S13P9","text":"","code":"Clocktonproposals13p9"}}','10302',''),
(10313,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10302','Clocktonproposals13p9a'),

-- SECTION XIV: HANDLING & CRITICAL SENSITIVE INFORMATION
(10314,'10','DES','','HANDLING & CRITICAL SENSITIVE INFORMATION (SENSITIVE INFORMATION AS DESCRIBED IN SECTION III.1. OF THIS APPLICATION)','','',''),
(10315,'10','DES','','Does the Applicant:','','10314',''),

(10316,'10','OPTIONAL','1','Have data classification/categorization measures in place?','{"S14P1":{"Id":"S14P1","text":"","code":"Clocktonproposals14p1"}}','10315',''),
(10317,'10','OPTIONAL','2','Isolate critical/sensitive information in its own segregated environment?','{"S14P2":{"Id":"S14P2","text":"","code":"Clocktonproposals14p2"}}','10315',''),
(10318,'10','OPTIONAL','3','Encrypt critical/sensitive information whilst at rest or in transit?','{"S14P3":{"Id":"S14P3","text":"","code":"Clocktonproposals14p3"}}','10315',''),
(10319,'10','OPTIONAL','4','Use additional security measures such as tokenization or salting where applicable?','{"S14P4":{"Id":"S14P4","text":"","code":"Clocktonproposals14p4"}}','10315',''),
(10320,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10315','Clocktonproposals14p4a'),

-- SECTION XV: MOBILE & PORTABLE DEVICES
(10321,'10','DES','','MOBILE & PORTABLE DEVICES','','',''),
(10322,'10','DES','','Does the Applicant:','','10321',''),

(10323,'10','OPTIONAL','1','Encrypt all sensitive data that is physically removed from your premises by laptop, mobile/portable devices, USB, backup tapes or other means?','{"S15P1":{"Id":"S15P1","text":"","code":"Clocktonproposals15p1"}}','10322',''),
(10324,'10','OPTIONAL','','If ''Yes'', do you require storage on mobile and portable devices to be encrypted?','{"S15P1A1":{"Id":"S15P1A1","text":"","code":"Clocktonproposals15p1a1"}}','10323',''),
(10325,'10','OPTIONAL','','If ''No'', please confirm whether you allow information to be downloaded onto portable devices.','{"S15P1A2":{"Id":"S15P1A2","text":"","code":"Clocktonproposals15p1a2"}}','10323',''),

(10326,'10','OPTIONAL','2','Allow Bring-Your-Own-Device (BYOD) connections to the business network? (If only allowed to connect to guest Wi-Fi, choose "No")','{"S15P2":{"Id":"S15P2","text":"","code":"Clocktonproposals15p2"}}','10322',''),
(10327,'10','OPTIONAL','','If ''Yes'', does the Applicant have a policy that governs BYOD usage and controls?','{"S15P2A":{"Id":"S15P2A","text":"","code":"Clocktonproposals15p2a"}}','10326',''),

(10328,'10','OPTIONAL','3','Use a mobile device management system (MDM), which gives the ability to remote wipe the devices?','{"S15P3":{"Id":"S15P3","text":"","code":"Clocktonproposals15p3"}}','10322',''),
(10329,'10','OPTIONAL','','If ''Yes'', is the MDM system applied to: Company-owned devices "BYOD" devices','{"S15P3A":{"Id":"S15P3A","text":"","code":"Clocktonproposals15p3a"}}','10328',''),

(10330,'10','OPTIONAL','4','Encrypt sensitive data when sent outside of its network (in transit)?','{"S15P4":{"Id":"S15P4","text":"","code":"Clocktonproposals15p4"}}','10322',''),
(10331,'10','SUB','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S15P4A":{"Id":"S15P4A","text":"","code":"Clocktonproposals15p4a"}}','10322',''),

-- SECTION XVI: DATA RECOVERY & NETWORK BUSINESS INTERRUPTION ASSESSMENT
(10332,'10','DES','','DATA RECOVERY & NETWORK BUSINESS INTERRUPTION ASSESSMENT','','',''),
(10333,'10','DES','','Does the Applicant:','','10332',''),

(10334,'10','OPTIONAL','1','Have any of the following plans in place to address security or data breaches:','{"S16P1R1":{"Id":"S16P1R1","text":"","code":"Clocktonproposals16p1r1"}}','10333',''),
(10335,'10','OPTIONAL','a','If ''Yes'', does the Applicant have a policy that governs BYOD usage and controls?','{"S16P1A":{"Id":"S16P1A","text":"","code":"Clocktonproposals16p1a"}}','10334',''),
(10336,'10','OPTIONAL','b','If ''Yes'', does the plan(s) include ransomware-specific response and recovery plans?','{"S16P1B":{"Id":"S16P1B","text":"","code":"Clocktonproposals16p1b"}}','10334',''),
(10337,'10','OPTIONAL','c','If ''Yes'', are the plan(s) tested at least annually with any critical deficiencies remediated?','{"S16P1C":{"Id":"S16P1C","text":"","code":"Clocktonproposals16p1c"}}','10334',''),
(10338,'10','OPTIONAL','d','If ''Yes'', are the plan(s) readily available in hardcopy?','{"S16P1D":{"Id":"S16P1D","text":"","code":"Clocktonproposals16p1d"}}','10334',''),

(10339,'10','OPTIONAL','2','Conduct cybersecurity incident tabletop exercises?','{"S16P2":{"Id":"S16P2","text":"","code":"Clocktonproposals16p2"}}','10333',''),
(10340,'10','SUB','a','Approximate date of last exercise?','{"S16P2A":{"Id":"S16P2A","text":"","code":"Clocktonproposals16p2a"}}','10339',''),
(10341,'10','OPTIONAL','b','Did the exercise include a threat from ransomware?','{"S16P2B":{"Id":"S16P2B","text":"","code":"Clocktonproposals16p2b"}}','10339',''),

(10342,'10','OPTIONAL','3','Track how long it takes to restore the Applicant''s vital assets following a network outage?','{"S16P3":{"Id":"S16P3","text":"","code":"Clocktonproposals16p3"}}','10333',''),
(10343,'10','OPTIONAL','','If so, the length of time is:','{"S16P3R1":{"Id":"S16P3R1","text":"","code":"Clocktonproposals16p3r1"}}','10342',''),

(10344,'10','OPTIONAL','4','Track how long it takes to restore the Applicant''s non-critical systems following a network outage?','{"S16P4":{"Id":"S16P4","text":"","code":"Clocktonproposals16p4"}}','10333',''),
(10345,'10','OPTIONAL','','If so, the length of time is:','{"S16P4R1":{"Id":"S16P4R1","text":"","code":"Clocktonproposals16p4r1"}}','10344',''),

(10346,'10','NORMAL','5','What is the Applicant''s Recovery Time Objective (RTO)?','','10333','Clocktonproposals16p5'),
(10347,'10','OPTIONAL','a','Does the Applicant test and meet the RTO?','{"S16P5A":{"Id":"S16P5A","text":"","code":"Clocktonproposals16p5a"}}','10346',''),
(10348,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10333','Clocktonproposals16p5b'),

-- SECTION XVII: LEGAL & REGULATORY
(10349,'10','DES','','LEGAL & REGULATORY','','',''),
(10350,'10','DES','','Does the Applicant:','','10349',''),

(10351,'10','OPTIONAL','','Have policies and procedures in place covering the following individuals'' rights under countries'' data protection regulations?','{"S17P0":{"Id":"S17P0","text":"","code":"Clocktonproposals17p0"}}','10350',''),
(10352,'10','OPTIONAL','1','Individuals are informed about the collection and use of their personal data','{"S17P1":{"Id":"S17P1","text":"","code":"Clocktonproposals17p1"}}','10350',''),
(10353,'10','OPTIONAL','2','Individuals have the right to access their personal data and a formal subject access request process is in place','{"S17P2":{"Id":"S17P2","text":"","code":"Clocktonproposals17p2"}}','10350',''),
(10354,'10','OPTIONAL','3','Individuals have the right to have inaccurate personal data rectified, or completed if it is incomplete, and a formal data rectification request process is in place','{"S17P3":{"Id":"S17P3","text":"","code":"Clocktonproposals17p3"}}','10350',''),
(10355,'10','OPTIONAL','4','Individuals have the right to have personal data erased and a formal data erasure process is in place','{"S17P4":{"Id":"S17P4","text":"","code":"Clocktonproposals17p4"}}','10350',''),
(10356,'10','OPTIONAL','5','Individuals have the right to obtain and reuse their personal data for their own purposes across different services and a formal data portability policy is in place','{"S17P5":{"Id":"S17P5","text":"","code":"Clocktonproposals17p5"}}','10350',''),
(10357,'10','OPTIONAL','6','Individuals have the right to object to the processing of their personal data and a formal objection policy is in place','{"S17P6":{"Id":"S17P6","text":"","code":"Clocktonproposals17p6"}}','10350',''),
(10358,'10','OPTIONAL','7','Have a lawful basis to carry out profiling and/or automated decision-making which is documented in our data protection policy','{"S17P7":{"Id":"S17P7","text":"","code":"Clocktonproposals17p7"}}','10350',''),

(10359,'10','OPTIONAL','8','Have a privacy policy? ','{"S17P8":{"Id":"S17P8","text":"","code":"Clocktonproposals17p8"}}','10350',''),
(10360,'10','DES','','If ''Yes''','','10359',''),
(10361,'10','OPTIONAL','a','Is the privacy policy displayed on the Applicant''s website? ','{"S17P8A":{"Id":"S17P8A","text":"","code":"Clocktonproposals17p8a"}}','10359',''),
(10362,'10','OPTIONAL','b','Is the privacy policy approved by the Applicant''s Board or legal department?','{"S17P8B":{"Id":"S17P8B","text":"","code":"Clocktonproposals17p8b"}}','10359',''),
(10363,'10','OPTIONAL','c','Is the privacy policy regularly reviewed and updated?','{"S17P8C":{"Id":"S17P8C","text":"","code":"Clocktonproposals17p8c"}}','10359',''),

(10364,'10','OPTIONAL','9','Have a written, Board-approved policy that addresses compliance with applicable privacy and security laws or regulations?','{"S17P9":{"Id":"S17P9","text":"","code":"Clocktonproposals17p9"}}','10350',''),
(10365,'10','NORMAL','','If you have answered ''No'' to any of the questions above, please provide an explanation and information on your plans for compliance below:','{"S17P9a":{"Id":"S17P9a","text":"","code":"Clocktonproposals17p9a"}}','10350',''),
(10366,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10350','Clocktonproposals17p10r1'),

-- SECTION XVIII: VENDOR MANAGEMENT
(10367,'10','DES','','VENDOR MANAGEMENT','','',''),
(10368,'10','DES','1','Please identify all vendors that have access to or help to manage the Applicant''s network or security systems:','','10367',''),

(10369,'10','NORMAL','','Data center hosting','{"S18P1AR1":{"Id":"S18P1AR1","text":"","code":"Clocktonproposals18p1ar1"}}','10368',''),
(10370,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1AR2":{"Id":"S18P1AR2","text":"","code":"Clocktonproposals18p1ar2"}}','10369',''),

(10371,'10','NORMAL','','Cloud services','{"S18P1BR1":{"Id":"S18P1BR1","text":"","code":"Clocktonproposals18p1br1"}}','10368',''),
(10372,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1BR2":{"Id":"S18P1BR2","text":"","code":"Clocktonproposals18p1br2"}}','10371',''),

(10373,'10','NORMAL','','Web hosting','{"S18P1CR1":{"Id":"S18P1CR1","text":"","code":"Clocktonproposals18p1cr1"}}','10368',''),
(10374,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1CR2":{"Id":"S18P1CR2","text":"","code":"Clocktonproposals18p1cr2"}}','10373',''),

(10375,'10','NORMAL','','Critical software','{"S18P1DR1":{"Id":"S18P1DR1","text":"","code":"Clocktonproposals18p1dr1"}}','10368',''),
(10376,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1DR2":{"Id":"S18P1DR2","text":"","code":"Clocktonproposals18p1dr2"}}','10375',''),

(10377,'10','NORMAL','','Managed security services','{"S18P1ER1":{"Id":"S18P1ER1","text":"","code":"Clocktonproposals18p1er1"}}','10368',''),
(10378,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1ER2":{"Id":"S18P1ER2","text":"","code":"Clocktonproposals18p1er2"}}','10377',''),

(10379,'10','NORMAL','','Data processing services','{"S18P1FR1":{"Id":"S18P1FR1","text":"","code":"Clocktonproposals18p1fr1"}}','10368',''),
(10380,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1FR2":{"Id":"S18P1FR2","text":"","code":"Clocktonproposals18p1fr2"}}','10379',''),

(10381,'10','NORMAL','','Endpoint detection and response','{"S18P1GR1":{"Id":"S18P1GR1","text":"","code":"Clocktonproposals18p1gr1"}}','10368',''),
(10382,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1GR2":{"Id":"S18P1GR2","text":"","code":"Clocktonproposals18p1gr2"}}','10381',''),

(10383,'10','NORMAL','','Antivirus','{"S18P1HR1":{"Id":"S18P1HR1","text":"","code":"Clocktonproposals18p1hr1"}}','10368',''),
(10384,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1HR2":{"Id":"S18P1HR2","text":"","code":"Clocktonproposals18p1hr2"}}','10383',''),

(10385,'10','NORMAL','','Firewall','{"S18P1IR1":{"Id":"S18P1IR1","text":"","code":"Clocktonproposals18p1ir1"}}','10368',''),
(10386,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1IR2":{"Id":"S18P1IR2","text":"","code":"Clocktonproposals18p1ir2"}}','10385',''),

(10387,'10','NORMAL','','Intrusion detection and prevention systems','{"S18P1JR1":{"Id":"S18P1JR1","text":"","code":"Clocktonproposals18p1jr1"}}','10368',''),
(10388,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1JR2":{"Id":"S18P1JR2","text":"","code":"Clocktonproposals18p1jr2"}}','10387',''),

(10389,'10','NORMAL','','Internet service provider','{"S18P1KR1":{"Id":"S18P1KR1","text":"","code":"Clocktonproposals18p1kr1"}}','10368',''),
(10390,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1KR2":{"Id":"S18P1KR2","text":"","code":"Clocktonproposals18p1kr2"}}','10389',''),

(10391,'10','NORMAL','','Data loss prevention','{"S18P1LR1":{"Id":"S18P1LR1","text":"","code":"Clocktonproposals18p1lr1"}}','10368',''),
(10392,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1LR2":{"Id":"S18P1LR2","text":"","code":"Clocktonproposals18p1lr2"}}','10391',''),

(10393,'10','NORMAL','','Recovery services','{"S18P1MR1":{"Id":"S18P1MR1","text":"","code":"Clocktonproposals18p1mr1"}}','10368',''),
(10394,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1MR2":{"Id":"S18P1MR2","text":"","code":"Clocktonproposals18p1mr2"}}','10393',''),

(10395,'10','NORMAL','','Other (please state):','{"S18P1NR1":{"Id":"S18P1NR1","text":"","code":"Clocktonproposals18p1nr1"}}','10368',''),
(10396,'10','OPTIONAL','','Does the vendor indemnify the Applicant under contract?','{"S18P1NR2":{"Id":"S18P1NR2","text":"","code":"Clocktonproposals18p1nr2"}}','10395',''),

(10397,'10','OPTIONAL','2','Are all vendors required to comply with the Applicant''s security policy?','{"S18P2":{"Id":"S18P2","text":"","code":"Clocktonproposals18p2"}}','10368',''),
(10398,'10','OPTIONAL','3','Are vendors audited to ensure that they meet the Applicant''s security and privacy standards as well as those customary in the relevant industry and those mandated by regulators?','{"S18P3":{"Id":"S18P3","text":"","code":"Clocktonproposals18p3"}}','10368',''),
(10399,'10','OPTIONAL','4','Are vendor access rights periodically reviewed and updated?','{"S18P4":{"Id":"S18P4","text":"","code":"Clocktonproposals18p4"}}','10368',''),
(10400,'10','OPTIONAL','5','Is vendor access on the Applicant''s network monitored?','{"S18P5":{"Id":"S18P5","text":"","code":"Clocktonproposals18p5"}}','10368',''),
(10401,'10','OPTIONAL','6','Is vendor access limited to dedicated time windows?','{"S18P6":{"Id":"S18P6","text":"","code":"Clocktonproposals18p6"}}','10368',''),
(10402,'10','OPTIONAL','7','Does the Applicant periodically review all contracts to ensure that they satisfy data security and privacy laws and regulations?','{"S18P7":{"Id":"S18P7","text":"","code":"Clocktonproposals18p7"}}','10368',''),
(10403,'10','OPTIONAL','8','Does the Applicant have a procedure to manage the termination of vendor contracts?','{"S18P8":{"Id":"S18P8","text":"","code":"Clocktonproposals18p8"}}','10368',''),
(10404,'10','OPTIONAL','9','Does the Applicant require vendors to have cyber insurance coverage?','{"S18P9":{"Id":"S18P9","text":"","code":"Clocktonproposals18p9"}}','10368',''),
(10405,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10368','Clocktonproposals18p9a'),

-- SECTION XIX: BIOMETRIC INFORMATION
(10406,'10','DES','','BIOMETRIC INFORMATION','','',''),
(10407,'10','DES','','Does the Applicant:','','10406',''),

(10408,'10','OPTIONAL','1','Collect, store, process, use or retain any biometric information? If yes, please complete the following section.','{"S19P1":{"Id":"S19P1","text":"","code":"Clocktonproposals19p1"}}','10407',''),
(10409,'10','OPTIONAL-NORMAL','2','Collect, receive, or retain any biometric data on employees or consumers as defined by law including (but not limited to):','{"S19P2R1":{"Id":"S19P2R1","text":"","code":"Clocktonproposals19p2r1"}}','10407',''),
(10410,'10','OPTIONAL','3','Clearly define to employees, consumers, and/or individuals how the Applicant will:','{"S19P3R1":{"Id":"S19P3R1","text":"","code":"Clocktonproposals19p3r1"}}','10407',''),
(10411,'10','OPTIONAL','4','Sell, lease, trade or otherwise profit from the biometric information of employees/consumers/individuals?','{"S19P4":{"Id":"S19P4","text":"","code":"Clocktonproposals19p4"}}','10407',''),
(10412,'10','OPTIONAL-NORMAL','5','Subject biometric information to the following measures? Select all that apply.','{"S19P5R1":{"Id":"S19P5R1","text":"","code":"Clocktonproposals19p5r1"}}','10407',''),

(10413,'10','OPTIONAL','6','Obtain written consent from employees/consumers/individuals prior to collection, receipt, or retention of biometric data?','{"S19P6":{"Id":"S19P6","text":"","code":"Clocktonproposals19p6"}}','10407',''),
(10414,'10','OPTIONAL','7','Have a retention schedule outlining how long biometric information is retained?','{"S19P7":{"Id":"S19P7","text":"","code":"Clocktonproposals19p7"}}','10407',''),
(10415,'10','OPTIONAL','8','Have a data destruction policy for biometric information that is no longer required?','{"S19P8":{"Id":"S19P8","text":"","code":"Clocktonproposals19p8"}}','10407',''),
(10416,'10','OPTIONAL-NORMAL','9','Has the Applicant received any complaints alleging the unlawful collection, use, dissemination, or sale of biometric data? If ''Yes'', please describe:','{"S19P9R1":{"Id":"S19P9R1","text":"","code":"Clocktonproposals19p9r1"},"S19P9R2":{"Id":"S19P9R2","text":"","code":"Clocktonproposals19p9r2"}}','10407',''),
(10417,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10407','Clocktonproposals19p9a'),

-- SECTION XX: PAYMENT CARD INDUSTRY ASSESSMENT
(10418,'10','DES','','PAYMENT CARD INDUSTRY ASSESSMENT','','',''),
(10419,'10','DES','','(complete only if applying for PCI DSS liability coverage)','','10418',''),

(10420,'10','OPTIONAL','1','Accept payment cards for its goods or services?','{"S20P1":{"Id":"S20P1","text":"","code":"Clocktonproposals20p1"}}','10419',''),
(10421,'10','OPTIONAL','','If ''Yes'', is the Applicant compliant with PCI DSS Security Standards?','{"S20P1R1":{"Id":"S20P1R1","text":"","code":"Clocktonproposals20p1r1"}}','10420',''),
(10422,'10','SUB','','If ''No'', please describe the current status of the Applicant''s compliance work:','{"S20P1R2":{"Id":"S20P1R2","text":"","code":"Clocktonproposals20p1r2"}}','10420',''),
(10423,'10','SUB','a','What Level of PCI Merchant is the Applicant?','{"S20P1A":{"Id":"S20P1A","text":"","code":"Clocktonproposals20p1a"}}','10420',''),
(10424,'10','SUB','b','Approximately how many transactions were processed during the last 12 months?','{"S20P1B":{"Id":"S20P1B","text":"","code":"Clocktonproposals20p1b"}}','10420',''),
(10425,'10','SUB','c','What is the approximate percentage of annual revenue attributable to credit card transactions?','{"S20P1C":{"Id":"S20P1C","text":"","code":"Clocktonproposals20p1c"}}','10420',''),

(10426,'10','OPTIONAL','2','Store payment card data on its network? ','{"S20P2":{"Id":"S20P2","text":"","code":"Clocktonproposals20p2"}}','10419',''),
(10427,'10','DES','','If ''Yes'': ','','10426',''),
(10428,'10','SUB','a','For how long is such data stored on the Applicant''s network?','{"S20P2A":{"Id":"S20P2A","text":"","code":"Clocktonproposals20p2a"}}','10426',''),
(10429,'10','OPTIONAL','b','Is payment card data either encrypted or tokenized at all times?','{"S20P2B":{"Id":"S20P2B","text":"","code":"Clocktonproposals20p2b"}}','10426',''),
(10430,'10','SUB','c','If the payment card data is not encrypted or tokenized, please describe what security protects such data in an appendix to this application.','{"S20P2C":{"Id":"S20P2C","text":"","code":"Clocktonproposals20p2c"}}','10426',''),

(10431,'10','OPTIONAL','3','Transact all payments through a payment processor? ','{"S20P3":{"Id":"S20P3","text":"","code":"Clocktonproposals20p3"}}','10419',''),
(10432,'10','DES','','If ''Yes'': ','','10431',''),
(10433,'10','SUB','a','Who is the payment processor? ','{"S20P3A":{"Id":"S20P3A","text":"","code":"Clocktonproposals20p3a"}}','10431',''),
(10434,'10','OPTIONAL','b','Has the payment processor provided evidence of its PCI DSS compliance to the Applicant? ','{"S20P3B":{"Id":"S20P3B","text":"","code":"Clocktonproposals20p3b"}}','10431',''),
(10435,'10','OPTIONAL','4','Are 100% of your point to sale terminals EMV compliant?','{"S20P4":{"Id":"S20P4","text":"","code":"Clocktonproposals20p4"}}','10419',''),
(10436,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','','10419','Clocktonproposals20p4a'),

-- SECTION XXI: MULTIMEDIA ASSESSMENT
(10437,'10','DES','','MULTIMEDIA ASSESSMENT','','',''),
(10438,'10','DES','','(Complete only if applying for multimedia liability coverage)','','10437',''),
(10439,'10','DES','','Does the Applicant:','','10437',''),

(10440,'10','DES','1','Have a process in place to review media content (website, social media or otherwise) for the following prior to publication?','','10439',''),
(10441,'10','OPTIONAL','a','Infringement of copyright?','{"S21P1A":{"Id":"S21P1A","text":"","code":"Clocktonproposals21p1a"}}','10440',''),
(10442,'10','OPTIONAL','b','Infringement of trademark?','{"S21P1B":{"Id":"S21P1B","text":"","code":"Clocktonproposals21p1b"}}','10440',''),
(10443,'10','OPTIONAL','c','Libel or slander?','{"S21P1C":{"Id":"S21P1C","text":"","code":"Clocktonproposals21p1c"}}','10440',''),
(10444,'10','OPTIONAL','d','Invasion of privacy?','{"S21P1D":{"Id":"S21P1D","text":"","code":"Clocktonproposals21p1d"}}','10440',''),

(10445,'10','OPTIONAL-NORMAL','2','Require a qualified attorney to review the above?, If ''No'', please describe the procedures to avoid the posting of improper or infringing content:','{"S21P2":{"Id":"S21P2","text":"","code":"Clocktonproposals21p2"}}','10439',''),
(10446,'10','OPTIONAL','3','Have a procedure for responding to any allegations which are in the nature of items 1. (i) to (iv) above?','{"S21P3":{"Id":"S21P3","text":"","code":"Clocktonproposals21p3"}}','10439',''),

(10447,'10','DES','4','In respect of the Applicant''s website:','','10439',''),
(10448,'10','OPTIONAL','a','Does the Applicant record visitor acceptance of terms of use before access is granted?','{"S21P4A":{"Id":"S21P4A","text":"","code":"Clocktonproposals21p4a"}}','10447',''),
(10449,'10','OPTIONAL','b','Does the website include third-party content?','{"S21P4B":{"Id":"S21P4B","text":"","code":"Clocktonproposals21p4b"}}','10447',''),
(10450,'10','DES','','If ''Yes'':','','10447',''),
(10451,'10','OPTIONAL','i','Does this content include streaming video and music?','{"S21P4B1":{"Id":"S21P4B1","text":"","code":"Clocktonproposals21p4b1"}}','10450',''),
(10452,'10','OPTIONAL','ii','Does the Applicant have procedures in place to secure rights for using all such third-party content?','{"S21P4B2":{"Id":"S21P4B2","text":"","code":"Clocktonproposals21p4b2"}}','10450',''),

(10453,'10','OPTIONAL','c','Does the Applicant allow third parties to post content directly to the website?','{"S21P4C":{"Id":"S21P4C","text":"","code":"Clocktonproposals21p4c"}}','10447',''),
(10454,'10','OPTIONAL','d','Does the Applicant monitor content for offensive, harassing, infringing or other undesirable material?','{"S21P4D":{"Id":"S21P4D","text":"","code":"Clocktonproposals21p4d"}}','10447',''),
(10455,'10','OPTIONAL','e','Does the Applicant reserve the right to remove or censor any content that violates the Applicant''s acceptable terms of use?','{"S21P4E":{"Id":"S21P4E","text":"","code":"Clocktonproposals21p4e"}}','10447',''),
(10456,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S21P4E1":{"Id":"S21P4E1","text":"","code":"Clocktonproposals21p4e1"}}','10439',''),

-- SECTION XXII: ADVERTISING ACTIVITIES
(10457,'10','DES','','Advertising activities','','',''),
(10458,'10','DES','1','Marketing/advertising costs','','10457',''),

(10459,'10','MULTIPLE','','U.S. costs','{"S22P1AR1":{"Id":"S22P1AR1","text":"Past fiscal year","code":"Clocktonproposals22p1ar1"},"S22P1AR2":{"Id":"S22P1AR2","text":"Current fiscal year","code":"Clocktonproposals22p1ar2"},"S22P1AR3":{"Id":"S22P1AR3","text":"Next fiscal year","code":"Clocktonproposals22p1ar3"}}','10458',''),
(10460,'10','MULTIPLE','','Non-U.S. costs','{"S22P1BR1":{"Id":"S22P1BR1","text":"Past fiscal year","code":"Clocktonproposals22p1br1"},"S22P1BR2":{"Id":"S22P1BR2","text":"Current fiscal year","code":"Clocktonproposals22p1br2"},"S22P1BR3":{"Id":"S22P1BR3","text":"Next fiscal year","code":"Clocktonproposals22p1br3"}}','10458',''),
(10461,'10','MULTIPLE','','Total costs','{"S22P1CR1":{"Id":"S22P1CR1","text":"Past fiscal year","code":"Clocktonproposals22p1cr1"},"S22P1CR2":{"Id":"S22P1CR2","text":"Current fiscal year","code":"Clocktonproposals22p1cr2"},"S22P1CR3":{"Id":"S22P1CR3","text":"Next fiscal year","code":"Clocktonproposals22p1cr3"}}','10458',''),

(10462,'10','DES','2','Advertising channels: Please indicate the approximate percentages of advertising/marketing spending in each of the following channels:','','10458',''),
(10463,'10','SUB','','Television/cable','{"S22P2A":{"Id":"S22P2A","text":"","code":"Clocktonproposals22p2a"}}','10462',''),
(10464,'10','SUB','','Direct mail/catalog (print)','{"S22P2B":{"Id":"S22P2B","text":"","code":"Clocktonproposals22p2b"}}','10462',''),
(10465,'10','SUB','','Newspapers (print)','{"S22P2C":{"Id":"S22P2C","text":"","code":"Clocktonproposals22p2c"}}','10462',''),
(10466,'10','SUB','','Digital/online (all channels)','{"S22P2D":{"Id":"S22P2D","text":"","code":"Clocktonproposals22p2d"}}','10462',''),
(10467,'10','SUB','','Magazines (print)','{"S22P2E":{"Id":"S22P2E","text":"","code":"Clocktonproposals22p2e"}}','10462',''),
(10468,'10','SUB','','Other, please describe:','{"S22P2F":{"Id":"S22P2F","text":"","code":"Clocktonproposals22p2f"}}','10462',''),

(10469,'10','OPTIONAL','3','Have a procedure for responding to any allegations which are in the nature of items 1. (i) to (iv) above?','{"S22P3":{"Id":"S22P3","text":"","code":"Clocktonproposals22p3"}}','10458',''),
(10470,'10','SUB','4','How many trade or service marks does the Applicant currently own?','{"S22P4":{"Id":"S22P4","text":"","code":"Clocktonproposals22p4"}}','10458',''),
(10471,'10','OPTIONAL','5','For the proposed policy period, does the Applicant plan to use any of the Applicant''s existing trade or service marks in connections with any new class(es) of goods or services for which the marks have not previously been used?','{"S22P5":{"Id":"S22P5","text":"","code":"Clocktonproposals22p5"}}','10458',''),
(10472,'10','OPTIONAL','6','Does the Applicant engage outside counsel specializing in trademark law in connections with the development or use of the Applicant''s marks and products?','{"S22P6":{"Id":"S22P6","text":"","code":"Clocktonproposals22p6"}}','10458',''),
(10473,'10','OPTIONAL','7','Does the Applicant always perform trademark clearance searches in connection with new marks or when expanding into new classes of goods or services?','{"S22P7":{"Id":"S22P7","text":"","code":"Clocktonproposals22p7"}}','10458',''),
(10474,'10','OPTIONAL','8','Does the Applicant operate an in-house advertising agency? (i.e., does the Applicant create advertising and/ or marketing content internally)?','{"S22P8":{"Id":"S22P8","text":"","code":"Clocktonproposals22p8"}}','10458',''),
(10475,'10','OPTIONAL','9','Does the Applicant employ outside advertising agencies to create advertising or marketing content?','{"S22P9":{"Id":"S22P9","text":"","code":"Clocktonproposals22p9"}}','10458',''),
(10476,'10','OPTIONAL','10','Does the Applicant utilize a website or social media to advertise or promote its products or services?','{"S22P10":{"Id":"S22P10","text":"","code":"Clocktonproposals22p10"}}','10458',''),
(10477,'10','OPTIONAL','11','Does the Applicant have a written employee social media policy?','{"S22P11":{"Id":"S22P11","text":"","code":"Clocktonproposals22p11"}}','10458',''),
(10478,'10','OPTIONAL','12','Does the Applicant have a process for legal review of all advertising, marketing, and promotional content, including website and social media content, prior to dissemination?','{"S22P12":{"Id":"S22P12","text":"","code":"Clocktonproposals22p12"}}','10458',''),
(10479,'10','OPTIONAL','13','Has the Applicant ever received notification that any of its advertising, marketing or promotional content infringes on the intellectual property rights of others?','{"S22P13":{"Id":"S22P13","text":"","code":"Clocktonproposals22p13"}}','10458',''),
(10480,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S22P13A":{"Id":"S22P13A","text":"","code":"Clocktonproposals22p13a"}}','10458',''),

-- SECTION XXIII: MEDIA RISK CONTROL & LEGAL REVIEW
(10481,'10','DES','','Media risk control & legal review','','',''),

(10482,'10','OPTIONAL','1','When providing technical, health-related or DIY related advice or guidance, does the Applicant always use a disclaimer or other warning?','{"S23P1":{"Id":"S23P1","text":"","code":"Clocktonproposals23p1"}}','10481',''),
(10483,'10','OPTIONAL','2','Does the Applicant have formal, written policies and procedures for addressing requests to remove allegedly offensive or infringing content disseminated by or on behalf of Applicant?','{"S23P2":{"Id":"S23P2","text":"","code":"Clocktonproposals23p2"}}','10481',''),
(10484,'10','OPTIONAL','3','Does the Applicant permit any User Generated Content ("UGC"), whether in the form of comments, videos, audio recordings, photographs/images, or other content, to be uploaded or shared on any of Applicant''s websites or mobile apps?','{"S23P3":{"Id":"S23P3","text":"","code":"Clocktonproposals23p3"}}','10481',''),
(10485,'10','SELECTOR-NORMAL','4','Please indicate which of the following additional quality control/risk management procedures the Applicant uses in connection with the Applicant''s media activities (select all that apply):','{"S23P4R1":{"Id":"S23P4R1","text":"","code":"Clocktonproposals23p4R1"},"S23P4R2":{"Id":"S23P4R2","text":"","code":"Clocktonproposals23p4R2"},"S23P4R3":{"Id":"S23P4R3","text":"","code":"Clocktonproposals23p4R3"},"S23P4R4":{"Id":"S23P4R4","text":"","code":"Clocktonproposals23p4R4"},"S23P4R5":{"Id":"S23P4R5","text":"Other (please identify)","code":"Clocktonproposals23p4R5"}}','10481',''),
(10486,'10','SUB','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S23P5":{"Id":"S23P5","text":"","code":"Clocktonproposals23p5"}}','10481',''),

-- SECTION XXIV: TECHNOLOGY E&O
(10487,'10','DES','','TECHNOLOGY E&O (INCLUDING MISCELLANEOUS PROFESSIONAL LIABILITY)','','',''),

(10488,'10','DES','1','Please provide a percentage breakdown of the Applicant''s annual revenue between the following activities:','','10487',''),
(10489,'10','DES','','Services and products','{"S24P0":{"Id":"S24P0","text":"Industries served","code":""},"S24P0B":{"Id":"S24P0B","text":"Estimated % of revenue","code":""},"S24P0C":{"Id":"S24P0C","text":"Length of time sold or provided","code":""}}','10488',''),

(10490,'10','DES','','Hardware','','10488',''),
(10491,'10','MULTIPLE','a','Sales of own brand','{"S24P1AR1":{"Id":"S24P1AR1","text":"","code":"Clocktonproposals24p1ar1"},"S24P1AR2":{"Id":"S24P1AR2","text":"","code":"Clocktonproposals24p1ar2"},"S24P1AR3":{"Id":"S24P1AR3","text":"","code":"Clocktonproposals24p1ar3"}}','10490',''),
(10492,'10','MULTIPLE','b','Distribution of other brands','{"S24P1BR1":{"Id":"S24P1BR1","text":"","code":"Clocktonproposals24p1br1"},"S24P1BR2":{"Id":"S24P1BR2","text":"","code":"Clocktonproposals24p1br2"},"S24P1BR3":{"Id":"S24P1BR3","text":"","code":"Clocktonproposals24p1br3"}}','10490',''),
(10493,'10','MULTIPLE','c','Installation','{"S24P1CR1":{"Id":"S24P1CR1","text":"","code":"Clocktonproposals24p1cr1"},"S24P1CR2":{"Id":"S24P1CR2","text":"","code":"Clocktonproposals24p1cr2"},"S24P1CR3":{"Id":"S24P1CR3","text":"","code":"Clocktonproposals24p1cr3"}}','10490',''),
(10494,'10','MULTIPLE','d','Maintenance','{"S24P1DR1":{"Id":"S24P1DR1","text":"","code":"Clocktonproposals24p1dr1"},"S24P1DR2":{"Id":"S24P1DR2","text":"","code":"Clocktonproposals24p1dr2"},"S24P1DR3":{"Id":"S24P1DR3","text":"","code":"Clocktonproposals24p1dr3"}}','10490',''),

(10495,'10','DES','','Software product sales','','10488',''),
(10496,'10','MULTIPLE','a','Sales of own brand shrink wrapped/off the shelf software','{"S24P2AR1":{"Id":"S24P2AR1","text":"","code":"Clocktonproposals24p2ar1"},"S24P2AR2":{"Id":"S24P2AR2","text":"","code":"Clocktonproposals24p2ar2"},"S24P2AR3":{"Id":"S24P2AR3","text":"","code":"Clocktonproposals24p2ar3"}}','10495',''),
(10497,'10','MULTIPLE','b','Distribution of other brand shrink wrapped/off the shelf software','{"S24P2BR1":{"Id":"S24P2BR1","text":"","code":"Clocktonproposals24p2br1"},"S24P2BR2":{"Id":"S24P2BR2","text":"","code":"Clocktonproposals24p2br2"},"S24P2BR3":{"Id":"S24P2BR3","text":"","code":"Clocktonproposals24p2br3"}}','10495',''),
(10498,'10','MULTIPLE','c','Customizable software','{"S24P2CR1":{"Id":"S24P2CR1","text":"","code":"Clocktonproposals24p2cr1"},"S24P2CR2":{"Id":"S24P2CR2","text":"","code":"Clocktonproposals24p2cr2"},"S24P2CR3":{"Id":"S24P2CR3","text":"","code":"Clocktonproposals24p2cr3"}}','10495',''),

(10499,'10','DES','','Software services','','10488',''),
(10500,'10','MULTIPLE','a','Installation, including configuration (no coding involved)','{"S24P3AR1":{"Id":"S24P3AR1","text":"","code":"Clocktonproposals24p3ar1"},"S24P3AR2":{"Id":"S24P3AR2","text":"","code":"Clocktonproposals24p3ar2"},"S24P3AR3":{"Id":"S24P3AR3","text":"","code":"Clocktonproposals24p3ar3"}}','10499',''),
(10501,'10','MULTIPLE','b','Customization (including coding changes)','{"S24P3BR1":{"Id":"S24P3BR1","text":"","code":"Clocktonproposals24p3br1"},"S24P3BR2":{"Id":"S24P3BR2","text":"","code":"Clocktonproposals24p3br2"},"S24P3BR3":{"Id":"S24P3BR3","text":"","code":"Clocktonproposals24p3br3"}}','10499',''),
(10502,'10','MULTIPLE','c','Maintenance','{"S24P3CR1":{"Id":"S24P3CR1","text":"","code":"Clocktonproposals24p3cr1"},"S24P3CR2":{"Id":"S24P3CR2","text":"","code":"Clocktonproposals24p3cr2"},"S24P3CR3":{"Id":"S24P3CR3","text":"","code":"Clocktonproposals24p3cr3"}}','10499',''),
(10503,'10','MULTIPLE','d','Systems integration','{"S24P3DR1":{"Id":"S24P3DR1","text":"","code":"Clocktonproposals24p3dr1"},"S24P3DR2":{"Id":"S24P3DR2","text":"","code":"Clocktonproposals24p3dr2"},"S24P3DR3":{"Id":"S24P3DR3","text":"","code":"Clocktonproposals24p3dr3"}}','10499',''),
(10504,'10','MULTIPLE','e','End-user applications','{"S24P3ER1":{"Id":"S24P3ER1","text":"","code":"Clocktonproposals24p3er1"},"S24P3ER2":{"Id":"S24P3ER2","text":"","code":"Clocktonproposals24p3er2"},"S24P3ER3":{"Id":"S24P3ER3","text":"","code":"Clocktonproposals24p3er3"}}','10499',''),

(10505,'10','DES','','Professional services','','10488',''),
(10506,'10','MULTIPLE','a','Consultancy','{"S24P4AR1":{"Id":"S24P4AR1","text":"","code":"Clocktonproposals24p4ar1"},"S24P4AR2":{"Id":"S24P4AR2","text":"","code":"Clocktonproposals24p4ar2"},"S24P4AR3":{"Id":"S24P4AR3","text":"","code":"Clocktonproposals24p4ar3"}}','10505',''),
(10507,'10','MULTIPLE','b','Contract staff','{"S24P4BR1":{"Id":"S24P4BR1","text":"","code":"Clocktonproposals24p4br1"},"S24P4BR2":{"Id":"S24P4BR2","text":"","code":"Clocktonproposals24p4br2"},"S24P4BR3":{"Id":"S24P4BR3","text":"","code":"Clocktonproposals24p4br3"}}','10505',''),
(10508,'10','MULTIPLE','c','Support services','{"S24P4CR1":{"Id":"S24P4CR1","text":"","code":"Clocktonproposals24p4cr1"},"S24P4CR2":{"Id":"S24P4CR2","text":"","code":"Clocktonproposals24p4cr2"},"S24P4CR3":{"Id":"S24P4CR3","text":"","code":"Clocktonproposals24p4cr3"}}','10505',''),
(10509,'10','MULTIPLE','d','Project management','{"S24P4DR1":{"Id":"S24P4DR1","text":"","code":"Clocktonproposals24p4dr1"},"S24P4DR2":{"Id":"S24P4DR2","text":"","code":"Clocktonproposals24p4dr2"},"S24P4DR3":{"Id":"S24P4DR3","text":"","code":"Clocktonproposals24p4dr3"}}','10505',''),
(10510,'10','MULTIPLE','e','Training','{"S24P4ER1":{"Id":"S24P4ER1","text":"","code":"Clocktonproposals24p4er1"},"S24P4ER2":{"Id":"S24P4ER2","text":"","code":"Clocktonproposals24p4er2"},"S24P4ER3":{"Id":"S24P4ER3","text":"","code":"Clocktonproposals24p4er3"}}','10505',''),
(10511,'10','MULTIPLE','f','Data management/ processing','{"S24P4FR1":{"Id":"S24P4FR1","text":"","code":"Clocktonproposals24p4fr1"},"S24P4FR2":{"Id":"S24P4FR2","text":"","code":"Clocktonproposals24p4fr2"},"S24P4FR3":{"Id":"S24P4FR3","text":"","code":"Clocktonproposals24p4fr3"}}','10505',''),
(10512,'10','MULTIPLE','g','Data communication services','{"S24P4GR1":{"Id":"S24P4GR1","text":"","code":"Clocktonproposals24p4gr1"},"S24P4GR2":{"Id":"S24P4GR2","text":"","code":"Clocktonproposals24p4gr2"},"S24P4GR3":{"Id":"S24P4GR3","text":"","code":"Clocktonproposals24p4gr3"}}','10505',''),
(10513,'10','MULTIPLE','h','Hosting services','{"S24P4HR1":{"Id":"S24P4HR1","text":"","code":"Clocktonproposals24p4hr1"},"S24P4HR2":{"Id":"S24P4HR2","text":"","code":"Clocktonproposals24p4hr2"},"S24P4HR3":{"Id":"S24P4HR3","text":"","code":"Clocktonproposals24p4hr3"}}','10505',''),
(10514,'10','MULTIPLE','i','Internet services','{"S24P4IR1":{"Id":"S24P4IR1","text":"","code":"Clocktonproposals24p4ir1"},"S24P4IR2":{"Id":"S24P4IR2","text":"","code":"Clocktonproposals24p4ir2"},"S24P4IR3":{"Id":"S24P4IR3","text":"","code":"Clocktonproposals24p4ir3"}}','10505',''),
(10515,'10','MULTIPLE','j','Other (please specify)','{"S24P4JR1":{"Id":"S24P4JR1","text":"","code":"Clocktonproposals24p4jr1"},"S24P4JR2":{"Id":"S24P4JR2","text":"","code":"Clocktonproposals24p4jr2"},"S24P4JR3":{"Id":"S24P4JR3","text":"","code":"Clocktonproposals24p4jr3"}}','10505',''),

(10516,'10','OPTIONAL','2','Does the Applicant have a formal quality assurance program?','{"S24P5":{"Id":"S24P5","text":"","code":"Clocktonproposals24p5"}}','10487',''),
(10517,'10','OPTIONAL','3','Does the Applicant have a formal project management methodology?','{"S24P6":{"Id":"S24P6","text":"","code":"Clocktonproposals24p6"}}','10487',''),
(10518,'10','OPTIONAL','4','Does the Applicant have a formal change control process?','{"S24P7":{"Id":"S24P7","text":"","code":"Clocktonproposals24p7"}}','10487',''),
(10519,'10','OPTIONAL','5','Does the Applicant have a formal software development methodology?','{"S24P8":{"Id":"S24P8","text":"","code":"Clocktonproposals24p8"}}','10487',''),
(10520,'10','OPTIONAL','6','Does the Applicant have a formal testing methodology?','{"S24P9":{"Id":"S24P9","text":"","code":"Clocktonproposals24p9"}}','10487',''),
(10521,'10','OPTIONAL','7','Does the Applicant have a formal disaster recovery plan?','{"S24P10":{"Id":"S24P10","text":"","code":"Clocktonproposals24p10"}}','10487',''),
(10522,'10','OPTIONAL','8','Does the Applicant have a formal business continuity plan?','{"S24P11":{"Id":"S24P11","text":"","code":"Clocktonproposals24p11"}}','10487',''),
(10523,'10','OPTIONAL','9','Does the Applicant have a formal data backup and recovery plan?','{"S24P12":{"Id":"S24P12","text":"","code":"Clocktonproposals24p12"}}','10487',''),
(10524,'10','OPTIONAL','10','Does the Applicant have a formal security policy?','{"S24P13":{"Id":"S24P13","text":"","code":"Clocktonproposals24p13"}}','10487',''),
(10525,'10','OPTIONAL','11','Does the Applicant have a formal privacy policy?','{"S24P14":{"Id":"S24P14","text":"","code":"Clocktonproposals24p14"}}','10487',''),
(10526,'10','OPTIONAL','12','Does the Applicant have a formal incident response plan?','{"S24P15":{"Id":"S24P15","text":"","code":"Clocktonproposals24p15"}}','10487',''),
(10527,'10','OPTIONAL','13','Does the Applicant have a formal vulnerability management program?','{"S24P16":{"Id":"S24P16","text":"","code":"Clocktonproposals24p16"}}','10487',''),
(10528,'10','OPTIONAL','14','Does the Applicant have a formal patch management program?','{"S24P17":{"Id":"S24P17","text":"","code":"Clocktonproposals24p17"}}','10487',''),
(10529,'10','OPTIONAL','15','Does the Applicant have a formal access control policy?','{"S24P18":{"Id":"S24P18","text":"","code":"Clocktonproposals24p18"}}','10487',''),
(10530,'10','OPTIONAL','16','Does the Applicant have a formal data classification policy?','{"S24P19":{"Id":"S24P19","text":"","code":"Clocktonproposals24p19"}}','10487',''),
(10531,'10','OPTIONAL','17','Does the Applicant have a formal data retention policy?','{"S24P20":{"Id":"S24P20","text":"","code":"Clocktonproposals24p20"}}','10487',''),
(10532,'10','OPTIONAL','18','Does the Applicant have a formal data disposal policy?','{"S24P21":{"Id":"S24P21","text":"","code":"Clocktonproposals24p21"}}','10487',''),
(10533,'10','OPTIONAL','19','Does the Applicant have a formal vendor management program?','{"S24P22":{"Id":"S24P22","text":"","code":"Clocktonproposals24p22"}}','10487',''),
(10534,'10','OPTIONAL','20','Does the Applicant have a formal risk management program?','{"S24P23":{"Id":"S24P23","text":"","code":"Clocktonproposals24p23"}}','10487',''),
(10535,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S24P23A":{"Id":"S24P23A","text":"","code":"Clocktonproposals24p23a"}}','10487',''),

-- SECTION XXV: CLAIMS HISTORY
(10536,'10','DES','','CLAIMS HISTORY','','',''),

(10537,'10','OPTIONAL','1','Has the Applicant or any predecessor entity ever had a claim made against it or been notified of circumstances which may give rise to a claim under any of the following types of insurance?','{"S25P1":{"Id":"S25P1","text":"","code":"Clocktonproposals25p1"}}','10536',''),
(10538,'10','OPTIONAL','a','Cyber/Privacy/Network Security','{"S25P1A":{"Id":"S25P1A","text":"","code":"Clocktonproposals25p1a"}}','10537',''),
(10539,'10','OPTIONAL','b','Technology Errors & Omissions','{"S25P1B":{"Id":"S25P1B","text":"","code":"Clocktonproposals25p1b"}}','10537',''),
(10540,'10','OPTIONAL','c','Media Liability','{"S25P1C":{"Id":"S25P1C","text":"","code":"Clocktonproposals25p1c"}}','10537',''),
(10541,'10','OPTIONAL','d','Directors & Officers','{"S25P1D":{"Id":"S25P1D","text":"","code":"Clocktonproposals25p1d"}}','10537',''),
(10542,'10','OPTIONAL','e','Employment Practices Liability','{"S25P1E":{"Id":"S25P1E","text":"","code":"Clocktonproposals25p1e"}}','10537',''),
(10543,'10','OPTIONAL','f','Fiduciary Liability','{"S25P1F":{"Id":"S25P1F","text":"","code":"Clocktonproposals25p1f"}}','10537',''),
(10544,'10','OPTIONAL','g','Crime/Fidelity','{"S25P1G":{"Id":"S25P1G","text":"","code":"Clocktonproposals25p1g"}}','10537',''),
(10545,'10','OPTIONAL','h','Kidnap & Ransom','{"S25P1H":{"Id":"S25P1H","text":"","code":"Clocktonproposals25p1h"}}','10537',''),

(10546,'10','DES','','If ''Yes'' to any of the above, please complete the following for each claim or circumstance:','','10537',''),
(10547,'10','NORMAL','a','Date of loss/incident:','','10546','Clocktonproposals25p1a1'),
(10548,'10','NORMAL','b','Type of coverage:','','10546','Clocktonproposals25p1b1'),
(10549,'10','NORMAL','c','Carrier:','','10546','Clocktonproposals25p1c1'),
(10550,'10','NORMAL','d','Policy limits:','','10546','Clocktonproposals25p1d1'),
(10551,'10','NORMAL','e','Deductible/SIR:','','10546','Clocktonproposals25p1e1'),
(10552,'10','NORMAL','f','Amount paid by carrier:','','10546','Clocktonproposals25p1f1'),
(10553,'10','NORMAL','g','Amount paid by Applicant:','','10546','Clocktonproposals25p1g1'),
(10554,'10','NORMAL','h','Status (open/closed):','','10546','Clocktonproposals25p1h1'),
(10555,'10','NORMAL','i','Brief description of claim/circumstance:','','10546','Clocktonproposals25p1i1'),

(10556,'10','OPTIONAL','2','Has the Applicant or any predecessor entity ever been the subject of any regulatory investigation or enforcement action by any government agency?','{"S25P2":{"Id":"S25P2","text":"","code":"Clocktonproposals25p2"}}','10536',''),
(10557,'10','SUB','','If ''Yes'', please provide details:','{"S25P2A":{"Id":"S25P2A","text":"","code":"Clocktonproposals25p2a"}}','10556',''),

(10558,'10','OPTIONAL','3','Has the Applicant or any predecessor entity ever been the subject of any class action lawsuit?','{"S25P3":{"Id":"S25P3","text":"","code":"Clocktonproposals25p3"}}','10536',''),
(10559,'10','SUB','','If ''Yes'', please provide details:','{"S25P3A":{"Id":"S25P3A","text":"","code":"Clocktonproposals25p3a"}}','10558',''),

(10560,'10','OPTIONAL','4','Has the Applicant or any predecessor entity ever had any insurance coverage declined, cancelled, or non-renewed?','{"S25P4":{"Id":"S25P4","text":"","code":"Clocktonproposals25p4"}}','10536',''),
(10561,'10','SUB','','If ''Yes'', please provide details:','{"S25P4A":{"Id":"S25P4A","text":"","code":"Clocktonproposals25p4a"}}','10560',''),

(10562,'10','OPTIONAL','5','Is the Applicant aware of any facts, circumstances, or situations which may reasonably be expected to give rise to a claim under the proposed policy?','{"S25P5":{"Id":"S25P5","text":"","code":"Clocktonproposals25p5"}}','10536',''),
(10563,'10','SUB','','If ''Yes'', please provide details:','{"S25P5A":{"Id":"S25P5A","text":"","code":"Clocktonproposals25p5a"}}','10562',''),

(10564,'10','NORMAL','','If the Applicant has any further comments on questions in the section above, please elaborate below:','{"S25P5B":{"Id":"S25P5B","text":"","code":"Clocktonproposals25p5b"}}','10536','');

-- Insert all options with corrected question ID references
INSERT INTO OPTIONS (IDQUESTION,OPTIONTEXT,CREATEDAT) VALUES
                                                          ('10020','Sole proprietor-Corporation-Partnership-##Other',''),

                                                          ('10028','yes-no-n/a',''),
                                                          ('10030','yes-no-n/a',''),
                                                          ('10034','yes-no-n/a',''),
                                                          ('10036','yes-no-n/a',''),
                                                          ('10037','yes-no-n/a',''),

                                                          ('10049','X',''),
                                                          ('10050','X',''),
                                                          ('10051','X',''),
                                                          ('10052','X',''),
                                                          ('10053','X',''),
                                                          ('10054','X',''),
                                                          ('10055','X-##Other (Add addendum)',''),

                                                          ('10057','yes-no-n/a',''),
                                                          ('10058','yes-no-n/a',''),
                                                          ('10059','yes-no-n/a',''),
                                                          ('10060','yes-no-n/a',''),
                                                          ('10061','yes-no-n/a',''),
                                                          ('10062','yes-no-n/a',''),
                                                          ('10063','yes-no-n/a',''),
                                                          ('10064','yes-no-n/a',''),

                                                          ('10065','yes-no-n/a',''),
                                                          ('10066','yes-no-n/a',''),
                                                          ('10067','yes-no-n/a',''),

                                                          ('10068','yes-no-n/a',''),
                                                          ('10069','Vital assets-Security operations-Data backup and recovery-Cloud transformation-Software development-##Other (please describe)',''),
                                                          ('10070','yes-no-n/a',''),

                                                          ('10071','yes-no-n/a',''),
                                                          ('10072','yes-no-n/a',''),
                                                          ('10073','yes-no-n/a',''),
                                                          ('10074','yes-no-n/a',''),
                                                          ('10075','yes-no-n/a',''),
                                                          ('10076','yes-no-n/a',''),
                                                          ('10077','yes-no-n/a',''),
                                                          ('10078','Asset name-Asset type-Asset owner-Asset location-Asset criticality-##Other',''),
                                                          ('10079','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),
                                                          ('10080','yes-no-n/a',''),
                                                          ('10081','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),
                                                          ('10082','yes-no-n/a',''),
                                                          ('10083','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),
                                                          ('10084','yes-no-n/a',''),
                                                          ('10085','yes-no-n/a',''),

                                                          ('10092','yes-no-n/a',''),
                                                          ('10093','yes-no-n/a',''),
                                                          ('10094','yes-no-n/a',''),
                                                          ('10095','yes-no-n/a',''),
                                                          ('10096','yes-no-n/a',''),
                                                          ('10097','yes-no-n/a',''),
                                                          ('10100','yes-no-n/a',''),

                                                          ('10104','yes-no-n/a',''),
                                                          ('10105','yes-no-n/a',''),
                                                          ('10106','yes-no-n/a',''),
                                                          ('10107','yes-no-n/a',''),
                                                          ('10108','yes-no-n/a',''),
                                                          ('10109','yes-no-n/a',''),

                                                          ('10115','yes-no-n/a',''),
                                                          ('10116','yes-no-n/a',''),
                                                          ('10117','yes-no-n/a',''),
                                                          ('10118','yes-no-n/a',''),
                                                          ('10119','yes-no-n/a',''),
                                                          ('10120','yes-no-n/a',''),
                                                          ('10121','yes-no-n/a',''),
                                                          ('10122','yes-no-n/a',''),
                                                          ('10123','yes-no-n/a',''),
                                                          ('10124','yes-no-n/a',''),
                                                          ('10125','yes-no-n/a',''),

                                                          ('10126','yes-no-n/a',''),
                                                          ('10127','yes-no-n/a',''),
                                                          ('10128','yes-no-n/a',''),
                                                          ('10129','yes-no-n/a',''),
                                                          ('10130','yes-no-n/a',''),

                                                          ('10132','SMS/Text-Hardware token-Software token-Push notification-Biometric-##Other',''),

                                                          ('10136','yes-no-n/a',''),
                                                          ('10137','yes-no-n/a',''),
                                                          ('10138','Domain administrator accounts-Service accounts-Local administrator accounts-##Other',''),
                                                          ('10140','yes-no-n/a',''),
                                                          ('10142','yes-no-n/a',''),
                                                          ('10144','yes-no-n/a',''),
                                                          ('10146','yes-no-n/a',''),
                                                          ('10147','yes-no-n/a',''),
                                                          ('10148','yes-no-n/a',''),
                                                          ('10149','yes-no-n/a',''),
                                                          ('10151','yes-no-n/a',''),
                                                          ('10152','yes-no-n/a',''),
                                                          ('10153','yes-no-n/a',''),
                                                          ('10154','yes-no-n/a',''),

                                                          ('10159','yes-no-n/a',''),
                                                          ('10160','yes-no-n/a',''),
                                                          ('10161','yes-no-n/a',''),
                                                          ('10162','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),
                                                          ('10165','yes-no-n/a',''),
                                                          ('10166','yes-no-n/a',''),
                                                          ('10167','yes-no-n/a',''),
                                                          ('10168','yes-no-n/a',''),
                                                          ('10169','yes-no-n/a',''),
                                                          ('10170','yes-no-n/a',''),
                                                          ('10171','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),

                                                          ('10178','Anti-malware-Application control-Device control-Firewall-Web protection-Email protection-##Other',''),
                                                          ('10179','yes-no-n/a',''),
                                                          ('10180','yes-no-n/a',''),

                                                          ('10187','yes-no-n/a',''),

                                                          ('10191','yes-no-n/a',''),
                                                          ('10192','yes-no-n/a',''),
                                                          ('10193','Email-Network-Both-##Other',''),
                                                          ('10194','yes-no-n/a',''),

                                                          ('10195','yes-no-n/a',''),
                                                          ('10196','yes-no-n/a',''),
                                                          ('10197','yes-no-n/a',''),
                                                          ('10198','yes-no-n/a',''),
                                                          ('10199','yes-no-n/a',''),
                                                          ('10200','yes-no-n/a',''),
                                                          ('10201','IDS-IPS-EPP-EDR-MDR-NDR-SIEM-DLP-##Other',''),

                                                          ('10202','yes-no-n/a',''),
                                                          ('10203','yes-no-n/a',''),
                                                          ('10204','yes-no-n/a',''),

                                                          ('10205','yes-no-n/a',''),
                                                          ('10206','yes-no-n/a',''),
                                                          ('10207','yes-no-n/a',''),
                                                          ('10208','yes-no-n/a',''),
                                                          ('10209','yes-no-n/a',''),
                                                          ('10210','yes-no-n/a',''),
                                                          ('10211','yes-no-n/a',''),
                                                          ('10212','yes-no-n/a',''),
                                                          ('10213','yes-no-n/a',''),

                                                          ('10214','yes-no-n/a',''),
                                                          ('10215','yes-no-n/a',''),
                                                          ('10217','Daily-Weekly-Monthly-Quarterly-Annually-##Other',''),
                                                          ('10218','yes-no-n/a',''),
                                                          ('10219','yes-no-n/a',''),
                                                          ('10220','yes-no-n/a',''),
                                                          ('10221','yes-no-n/a',''),
                                                          ('10222','yes-no-n/a',''),
                                                          ('10223','yes-no-n/a',''),
                                                          ('10224','yes-no-n/a',''),
                                                          ('10225','Daily-Weekly-Monthly-Quarterly-Annually-##Other',''),
                                                          ('10226','yes-no-n/a',''),
                                                          ('10227','yes-no-n/a',''),
                                                          ('10228','yes-no-n/a',''),
                                                          ('10229','yes-no-n/a',''),
                                                          ('10230','yes-no-n/a',''),
                                                          ('10231','yes-no-n/a',''),
                                                          ('10232','Annually-Quarterly-Monthly-Weekly-Daily-##Other',''),

                                                          ('10235','Microsoft Exchange-Microsoft 365-Google Workspace-##Other',''),
                                                          ('10236','yes-no-n/a',''),
                                                          ('10237','yes-no-n/a',''),

                                                          ('10240','yes-no-n/a',''),
                                                          ('10241','yes-no-n/a',''),
                                                          ('10242','yes-no-n/a',''),

                                                          ('10243','yes-no-n/a',''),
                                                          ('10244','SPF-DKIM-DMARC-##Other',''),

                                                          ('10245','yes-no-n/a',''),
                                                          ('10246','Weekly-Monthly-Quarterly-Annually-##Other',''),

                                                          ('10247','yes-no-n/a',''),

                                                          ('10249','yes-no-n/a',''),
                                                          ('10250','yes-no-n/a',''),
                                                          ('10251','yes-no-n/a',''),
                                                          ('10252','yes-no-n/a',''),
                                                          ('10253','yes-no-n/a',''),
                                                          ('10254','yes-no-n/a',''),
                                                          ('10255','yes-no-n/a',''),
                                                          ('10256','yes-no-n/a',''),

                                                          ('10257','POP3-IMAP-SMTP-##Other',''),

                                                          ('10260','yes-no-n/a',''),
                                                          ('10261','Within 24 hours-Within 48 hours-Within 72 hours-Within 1 week-Within 1 month-##Other',''),
                                                          ('10262','Workstations-Servers-Network devices-##Other',''),
                                                          ('10263','yes-no-n/a',''),

                                                          ('10265','yes-no-n/a',''),
                                                          ('10266','Within 1 week-Within 1 month-Within 3 months-Within 6 months-##Other',''),
                                                          ('10267','Workstations-Servers-Network devices-##Other',''),

                                                          ('10268','yes-no-n/a',''),
                                                          ('10269','yes-no-n/a',''),
                                                          ('10270','yes-no-n/a',''),
                                                          ('10271','yes-no-n/a',''),

                                                          ('10274','yes-no-n/a',''),

                                                          ('10278','yes-no-n/a',''),
                                                          ('10279','Daily-Weekly-Monthly-Quarterly-Annually-##Other',''),
                                                          ('10280','yes-no-n/a',''),
                                                          ('10281','On-site-Off-site-Cloud-Offline storage-##Other',''),

                                                          ('10283','yes-no-n/a',''),
                                                          ('10284','yes-no-n/a',''),
                                                          ('10285','yes-no-n/a',''),
                                                          ('10286','yes-no-n/a',''),

                                                          ('10287','Weekly-Monthly-Quarterly-Annually-##Other',''),
                                                          ('10288','Weekly-Monthly-Quarterly-Annually-##Other',''),

                                                          ('10289','Encryption-Authentication-Integrity checking-##Other',''),
                                                          ('10290','yes-no-n/a',''),

                                                          ('10291','yes-no-n/a',''),
                                                          ('10292','yes-no-n/a',''),
                                                          ('10293','yes-no-n/a',''),
                                                          ('10294','yes-no-n/a',''),

                                                          ('10295','yes-no-n/a',''),
                                                          ('10296','Weekly-Monthly-Quarterly-Annually-##Other',''),

                                                          ('10297','yes-no-n/a',''),
                                                          ('10298','yes-no-n/a',''),
                                                          ('10299','Less than 1 hour-1-4 hours-4-8 hours-8-24 hours-More than 24 hours-##Other',''),

                                                          ('10303','yes-no-n/a',''),
                                                          ('10304','yes-no-n/a',''),
                                                          ('10305','yes-no-n/a',''),
                                                          ('10306','yes-no-n/a',''),
                                                          ('10307','yes-no-n/a',''),
                                                          ('10308','yes-no-n/a',''),
                                                          ('10309','yes-no-n/a',''),
                                                          ('10310','yes-no-n/a',''),
                                                          ('10311','yes-no-n/a',''),
                                                          ('10312','yes-no-n/a',''),

                                                          ('10316','yes-no-n/a',''),
                                                          ('10317','yes-no-n/a',''),
                                                          ('10318','yes-no-n/a',''),
                                                          ('10319','yes-no-n/a',''),

                                                          ('10323','yes-no-n/a',''),
                                                          ('10324','yes-no-n/a',''),
                                                          ('10325','yes-no-n/a',''),
                                                          ('10326','yes-no-n/a',''),
                                                          ('10327','yes-no-n/a',''),
                                                          ('10328','yes-no-n/a',''),
                                                          ('10329','Company-owned devices-BYOD devices-Both-##Other',''),
                                                          ('10330','yes-no-n/a',''),

                                                          ('10334','Incident response plan-Business continuity plan-Disaster recovery plan-##Other',''),
                                                          ('10335','yes-no-n/a',''),
                                                          ('10336','yes-no-n/a',''),
                                                          ('10337','yes-no-n/a',''),
                                                          ('10338','yes-no-n/a',''),

                                                          ('10339','yes-no-n/a',''),
                                                          ('10341','yes-no-n/a',''),

                                                          ('10342','yes-no-n/a',''),
                                                          ('10343','Less than 1 hour-1-4 hours-4-8 hours-8-24 hours-More than 24 hours-##Other',''),

                                                          ('10344','yes-no-n/a',''),
                                                          ('10345','Less than 1 hour-1-4 hours-4-8 hours-8-24 hours-More than 24 hours-##Other',''),

                                                          ('10347','yes-no-n/a',''),

                                                          ('10351','yes-no-n/a',''),
                                                          ('10352','yes-no-n/a',''),
                                                          ('10353','yes-no-n/a',''),
                                                          ('10354','yes-no-n/a',''),
                                                          ('10355','yes-no-n/a',''),
                                                          ('10356','yes-no-n/a',''),
                                                          ('10357','yes-no-n/a',''),
                                                          ('10358','yes-no-n/a',''),

                                                          ('10359','yes-no-n/a',''),
                                                          ('10361','yes-no-n/a',''),
                                                          ('10362','yes-no-n/a',''),
                                                          ('10363','yes-no-n/a',''),

                                                          ('10364','yes-no-n/a',''),

                                                          ('10370','yes-no-n/a',''),
                                                          ('10372','yes-no-n/a',''),
                                                          ('10374','yes-no-n/a',''),
                                                          ('10376','yes-no-n/a',''),
                                                          ('10378','yes-no-n/a',''),
                                                          ('10380','yes-no-n/a',''),
                                                          ('10382','yes-no-n/a',''),
                                                          ('10384','yes-no-n/a',''),
                                                          ('10386','yes-no-n/a',''),
                                                          ('10388','yes-no-n/a',''),
                                                          ('10390','yes-no-n/a',''),
                                                          ('10392','yes-no-n/a',''),
                                                          ('10394','yes-no-n/a',''),
                                                          ('10396','yes-no-n/a',''),

                                                          ('10397','yes-no-n/a',''),
                                                          ('10398','yes-no-n/a',''),
                                                          ('10399','yes-no-n/a',''),
                                                          ('10400','yes-no-n/a',''),
                                                          ('10401','yes-no-n/a',''),
                                                          ('10402','yes-no-n/a',''),
                                                          ('10403','yes-no-n/a',''),
                                                          ('10404','yes-no-n/a',''),

                                                          ('10408','yes-no-n/a',''),
                                                          ('10409','Fingerprints-Palm prints-Facial recognition-Iris scans-Voice recognition-##Other',''),
                                                          ('10410','Collection-Storage-Processing-Use-Retention-##Other',''),
                                                          ('10411','yes-no-n/a',''),
                                                          ('10412','Encryption-Access controls-Audit logging-##Other',''),

                                                          ('10413','yes-no-n/a',''),
                                                          ('10414','yes-no-n/a',''),
                                                          ('10415','yes-no-n/a',''),
                                                          ('10416','yes-no-n/a',''),

                                                          ('10420','yes-no-n/a',''),
                                                          ('10421','yes-no-n/a',''),

                                                          ('10426','yes-no-n/a',''),
                                                          ('10429','yes-no-n/a',''),

                                                          ('10431','yes-no-n/a',''),
                                                          ('10434','yes-no-n/a',''),
                                                          ('10435','yes-no-n/a',''),

                                                          ('10441','yes-no-n/a',''),
                                                          ('10442','yes-no-n/a',''),
                                                          ('10443','yes-no-n/a',''),
                                                          ('10444','yes-no-n/a',''),

                                                          ('10445','yes-no-n/a',''),
                                                          ('10446','yes-no-n/a',''),

                                                          ('10448','yes-no-n/a',''),
                                                          ('10449','yes-no-n/a',''),
                                                          ('10451','yes-no-n/a',''),
                                                          ('10452','yes-no-n/a',''),
                                                          ('10453','yes-no-n/a',''),
                                                          ('10454','yes-no-n/a',''),
                                                          ('10455','yes-no-n/a',''),

                                                          ('10469','yes-no-n/a',''),
                                                          ('10471','yes-no-n/a',''),
                                                          ('10472','yes-no-n/a',''),
                                                          ('10473','yes-no-n/a',''),
                                                          ('10474','yes-no-n/a',''),
                                                          ('10475','yes-no-n/a',''),
                                                          ('10476','yes-no-n/a',''),
                                                          ('10477','yes-no-n/a',''),
                                                          ('10478','yes-no-n/a',''),
                                                          ('10479','yes-no-n/a',''),

                                                          ('10482','yes-no-n/a',''),
                                                          ('10483','yes-no-n/a',''),
                                                          ('10484','yes-no-n/a',''),
                                                          ('10485','Legal review-Fact checking-Source verification-Editorial review-##Other',''),

                                                          ('10516','yes-no-n/a',''),
                                                          ('10517','yes-no-n/a',''),
                                                          ('10518','yes-no-n/a',''),
                                                          ('10519','yes-no-n/a',''),
                                                          ('10520','yes-no-n/a',''),
                                                          ('10521','yes-no-n/a',''),
                                                          ('10522','yes-no-n/a',''),
                                                          ('10523','yes-no-n/a',''),
                                                          ('10524','yes-no-n/a',''),
                                                          ('10525','yes-no-n/a',''),
                                                          ('10526','yes-no-n/a',''),
                                                          ('10527','yes-no-n/a',''),
                                                          ('10528','yes-no-n/a',''),
                                                          ('10529','yes-no-n/a',''),
                                                          ('10530','yes-no-n/a',''),
                                                          ('10531','yes-no-n/a',''),
                                                          ('10532','yes-no-n/a',''),
                                                          ('10533','yes-no-n/a',''),
                                                          ('10534','yes-no-n/a',''),

                                                          ('10537','yes-no-n/a',''),
                                                          ('10538','yes-no-n/a',''),
                                                          ('10539','yes-no-n/a',''),
                                                          ('10540','yes-no-n/a',''),
                                                          ('10541','yes-no-n/a',''),
                                                          ('10542','yes-no-n/a',''),
                                                          ('10543','yes-no-n/a',''),
                                                          ('10544','yes-no-n/a',''),
                                                          ('10545','yes-no-n/a',''),

                                                          ('10556','yes-no-n/a',''),
                                                          ('10558','yes-no-n/a',''),
                                                          ('10560','yes-no-n/a',''),
                                                          ('10562','yes-no-n/a','');
