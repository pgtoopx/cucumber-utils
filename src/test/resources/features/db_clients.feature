@Ignore
Feature: Test clients

  Scenario: Test MYSQL client select
    Given SQL data source from file path "config/database/mysql.properties"
    Then SQL execute query "select * from gift_wf_2012 order by person_id asc limit 3" and compare result with
      | person_id | gift                                                 |
      | .*        | .*                                                   |
      | 21189037  | fun & joy for everybody!                             |
      | 21193939  | Leica M9-P Hermes Edition: http://vimeo.com/42108675 |

  Scenario: TEST MYSQL client delete
    Given SQL data source from file path "config/database/mysql.properties"
    Then SQL execute update "delete from gift_wf_2012 where person_id<100"

  Scenario: Test MYSQL client simple insert
    Given SQL data source from file path "config/database/mysql.properties"
    Then SQL execute update "insert into gift_wf_2012 (person_id,gift) values ('10','some test')"

  Scenario: Test MYSQL client simple insert with tabular data
    Given SQL data source from file path "config/database/mysql.properties"
    Then SQL INSERT into table "gift_wf_2012" the following data
      | person_id | gift              |
      | 14        | http://heheheh.ro |
      | 16        | null              |
      | 17        | wow               |

  Scenario: Test MYSQL update table with tabular data
    Given SQL data source from file path "config/database/mysql.properties"
    Then SQL UPDATE table "gift_wf_2012" WHERE "person_id=124325 and gift='wa'" with the following data
      | gift |
      | null |

  Scenario: Test POSTGRESQL client
    Given SQL data source from file path "config/database/psql.properties"
    Then SQL execute query "select * from external_domain order by id asc limit 2" and compare result with
      | provisioning_id | version |
      | 3929585         | 261951  |
      | 3929581         | 262173  |

  Scenario: Test SYBASE client
    Given SQL data source from file path "config/database/sybase.properties"
    Then SQL execute query "select TOP 2 domainname, adminc from domain order by domain_id asc" and compare result with
      | domainname                | adminc |
      | hypothekenfinanzierung.de | 1549   |
      | asb-bw.de                 | 1568   |





