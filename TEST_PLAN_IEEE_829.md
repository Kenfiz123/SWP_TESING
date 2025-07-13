# TEST PLAN OUTLINE (IEEE 829 FORMAT)
**SWP_TESING - Billiard Management System**

---

## 1. TEST PLAN IDENTIFIER

**Version:** 1.0  
**Name:** SWP_TESING Test Plan  
**Github Link:** https://github.com/Kenfiz123/SWP_TESING.git  
**Date:** 2025-07-14  
**Author(s):**  
- Do Phuc Duy - SE196419  
- Le Nguyen Tuan Khoa - SE194755  
- Phan Thanh Tuấn - SE184480  
- Nguyen Huu Thanh - SE173494  

**Revision History:**  
- v1.0 - Initial draft (2025-07-14)

---

## 2. REFERENCES

- **Software Requirements Specification (SRS):** Billiard Management System Requirements v1.0
- **Architecture Design Document:** Spring Boot REST API Architecture v1.0
- **Jira Board:** SWP_TESING Project Board
- **SonarQube Dashboard:** Code Quality Metrics
- **GitHub Repository:** https://github.com/Kenfiz123/SWP_TESING.git
- **Maven Project:** pom.xml configuration

---

## 3. INTRODUCTION

**Purpose:** This test plan defines the comprehensive testing strategy for the SWP_TESING billiard management system, covering unit, integration, system, and acceptance testing phases.

**Scope:** 
- Core business logic testing (User management, Ticket processing, Voucher validation)
- REST API endpoint testing
- Database integration testing
- Payment gateway integration (PayPal, MoMo)
- Authentication and authorization testing

**Constraints:** 
- Limited test window (2 weeks)
- Desktop web application focus
- Third-party payment APIs simulated via mocking
- Spring Boot framework constraints

---

## 4. TEST ITEMS (FUNCTIONS)

### 4.1 Core Business Functions
- **User Management System** (Version 1.0)
  - User registration and authentication
  - Role-based access control (Admin, Staff, Customer)
  - Profile management and password reset

- **Ticket Management System** (Version 1.0)
  - Ticket creation and validation
  - Status tracking (Pending, Approved, Rejected, Completed)
  - Feedback and review system

- **Voucher Management System** (Version 1.0)
  - Voucher creation and validation
  - Usage tracking and expiration handling
  - Discount calculation (percentage and fixed amount)

- **Payment Processing System** (Version 1.0)
  - PayPal integration
  - MoMo payment integration
  - Transaction history and reconciliation

### 4.2 Supporting Systems
- **Notification System** (Version 1.0)
- **Review and Rating System** (Version 1.0)
- **Admin Dashboard** (Version 1.0)

### 4.3 Database and Infrastructure
- **MySQL Database** (Version 8.0)
- **Spring Boot Application** (Version 3.0)
- **JPA/Hibernate ORM** (Version 6.0)

---

## 5. SOFTWARE RISK ISSUES

### 5.1 High Risk
- **Authentication failures** due to incorrect JWT token handling
- **Payment processing errors** leading to financial discrepancies
- **Data integrity issues** in ticket and voucher management
- **Security vulnerabilities** in user data handling

### 5.2 Medium Risk
- **Incomplete test coverage** for complex business logic
- **Poor integration** with third-party payment services
- **Performance degradation** under high user load
- **Database connection failures** during peak usage

### 5.3 Low Risk
- **UI/UX inconsistencies** across different browsers
- **Minor validation errors** in form inputs

---

## 6. FEATURES TO BE TESTED

### 6.1 Authentication & Authorization (Risk: High)
- User login/logout functionality
- JWT token generation and validation
- Role-based access control
- Password reset functionality
- Session management

### 6.2 Ticket Management (Risk: High)
- Ticket creation with validation
- Status transitions (Pending → Approved → Completed)
- Voucher application and discount calculation
- Feedback submission and review
- Admin ticket management

### 6.3 Voucher System (Risk: High)
- Voucher creation and validation
- Usage limit enforcement
- Expiration date handling
- Discount calculation (percentage vs fixed amount)
- Voucher code redemption

### 6.4 Payment Processing (Risk: High)
- PayPal payment integration
- MoMo payment integration
- Transaction status tracking
- Payment confirmation and receipt generation
- Refund processing

### 6.5 User Management (Risk: Medium)
- User registration and profile management
- Admin user management
- Staff assignment and management
- Customer data handling

### 6.6 Notification System (Risk: Medium)
- Email notification delivery
- In-app notification generation
- Notification preferences management

---

## 7. FEATURES NOT TO BE TESTED

- **UI Styling and Layout** (Reason: Handled by frontend team separately)
- **Performance Load Testing** (Reason: Managed by dedicated performance testing team)
- **Mobile Application** (Reason: Current release targets desktop web only)
- **Third-party API Endpoints** (Reason: Simulated via mocking for controlled testing)
- **Database Backup/Recovery** (Reason: Infrastructure team responsibility)

---

## 8. APPROACH (STRATEGY)

### 8.1 Unit Testing
- **Framework:** JUnit 5 + Mockito
- **Coverage:** 100% statement and decision coverage for business logic
- **Focus:** Individual method testing with mocked dependencies

### 8.2 Integration Testing
- **Framework:** Spring Boot Test + TestContainers
- **Focus:** Component interaction and database integration
- **Scope:** Service layer and repository layer integration

### 8.3 System Testing
- **Framework:** REST Assured + Spring Boot Test
- **Focus:** End-to-end API testing
- **Scope:** Complete user workflows

### 8.4 Acceptance Testing
- **Framework:** Cucumber + Selenium (if needed)
- **Focus:** Business requirement validation
- **Participants:** Stakeholders and business analysts

### 8.5 Quality Assurance
- **Code Coverage:** JaCoCo for coverage reporting
- **Code Quality:** SonarQube for static analysis
- **Continuous Integration:** GitHub Actions for automated testing

---

## 9. ITEM PASS/FAIL CRITERIA

### 9.1 Pass Criteria
- All test cases execute successfully with expected results
- Code coverage ≥ 90% for business logic
- Zero critical and high-severity bugs
- All acceptance criteria met by stakeholders

### 9.2 Fail Criteria
- Any test case fails to meet expected results
- Critical functionality broken
- Security vulnerabilities detected
- Performance degradation below acceptable thresholds

---

## 10. SUSPENSION CRITERIA AND RESUMPTION REQUIREMENTS

### 10.1 Suspension Criteria
- Critical security vulnerabilities discovered
- Database corruption or data loss
- Payment processing system failure
- Authentication system compromise

### 10.2 Resumption Requirements
- Critical issues resolved and verified
- Security fixes deployed and tested
- Database integrity restored
- Payment system functionality confirmed

---

## 11. TEST DELIVERABLES

- **Test Plan Document** (this document)
- **Test Cases and Scripts** (JUnit test classes)
- **Test Execution Results** (Surefire reports)
- **Code Coverage Reports** (JaCoCo HTML reports)
- **Bug Reports** (Jira tickets)
- **Test Summary Report** (Final testing report)
- **Unit Test Documentation** (UNIT_TEST_DOCUMENTATION.md)

---

## 12. REMAINING TEST TASKS

- [ ] Complete unit tests for all service classes
- [ ] Implement integration tests for payment gateways
- [ ] Create system tests for complete user workflows
- [ ] Generate final test coverage reports
- [ ] Prepare stakeholder acceptance testing
- [ ] Document test results and lessons learned

---

## 13. ENVIRONMENTAL NEEDS

### 13.1 Development Environment
- **Operating System:** Windows 10/11, Linux
- **Java Version:** JDK 17 or higher
- **IDE:** IntelliJ IDEA or Eclipse
- **Build Tool:** Maven 3.6+

### 13.2 Testing Environment
- **Database:** MySQL 8.0 (test instance)
- **Application Server:** Spring Boot embedded Tomcat
- **Test Frameworks:** JUnit 5, Mockito, TestContainers
- **Coverage Tool:** JaCoCo

### 13.3 CI/CD Environment
- **Version Control:** GitHub
- **CI/CD Pipeline:** GitHub Actions
- **Quality Gates:** SonarQube
- **Test Reporting:** Surefire + JaCoCo

---

## 14. STAFFING AND TRAINING NEEDS

### 14.1 Team Composition
- **Test Lead:** 1 (experienced in Spring Boot testing)
- **QA Engineers:** 2 (familiar with JUnit/Mockito)
- **Developers:** 4 (responsible for unit testing)
- **DevOps Engineer:** 1 (CI/CD setup and maintenance)

### 14.2 Training Requirements
- Spring Boot testing best practices
- Mockito advanced features
- TestContainers for integration testing
- JaCoCo coverage analysis
- GitHub Actions CI/CD

---

## 15. RESPONSIBILITIES

### 15.1 Test Case Design
- **Primary:** QA Engineers
- **Secondary:** Developers (unit test design)

### 15.2 Test Execution
- **Unit Testing:** Developers
- **Integration Testing:** QA Engineers
- **System Testing:** QA Engineers
- **Acceptance Testing:** Stakeholders + QA Lead

### 15.3 Test Management
- **Test Planning:** Test Lead
- **Bug Tracking:** QA Engineers
- **Reporting:** Test Lead
- **Quality Assurance:** DevOps Engineer

---

## 16. SCHEDULE

### 16.1 Test Planning Phase (Week 1)
- **Days 1-2:** Test plan finalization and review
- **Days 3-4:** Test case design and peer review
- **Day 5:** Environment setup and tool configuration

### 16.2 Test Execution Phase (Week 2)
- **Days 1-3:** Unit testing and code coverage
- **Days 4-5:** Integration and system testing
- **Day 6:** Bug fixing and retesting
- **Day 7:** Final reporting and documentation

### 16.3 Key Milestones
- **July 14:** Test plan approval
- **July 18:** Unit testing completion
- **July 21:** Integration testing completion
- **July 22:** Final test report submission

---

## 17. PLANNING RISKS AND CONTINGENCIES

### 17.1 Technical Risks
- **Risk:** Payment gateway API changes
  - **Mitigation:** Comprehensive mocking strategy
- **Risk:** Database performance issues
  - **Mitigation:** Test data optimization and indexing
- **Risk:** Third-party service unavailability
  - **Mitigation:** Mock-based testing approach

### 17.2 Schedule Risks
- **Risk:** Developer build delays
  - **Mitigation:** Parallel test case development
- **Risk:** Incomplete requirements
  - **Mitigation:** Iterative testing approach
- **Risk:** Resource unavailability
  - **Mitigation:** Cross-training and backup resources

---

## 18. APPROVALS

- **QA Lead:** _________________ (Date: ________)
- **Development Manager:** _________________ (Date: ________)
- **Product Owner:** _________________ (Date: ________)
- **Technical Lead:** _________________ (Date: ________)

---

## 19. GLOSSARY

- **JUnit:** Unit testing framework for Java applications
- **Mockito:** Java-based mocking framework for unit tests
- **JaCoCo:** Java Code Coverage Library for coverage analysis
- **TestContainers:** Java library for integration testing with Docker
- **SonarQube:** Continuous inspection platform for code quality
- **Jira:** Issue tracking and project management tool
- **GitHub Actions:** CI/CD platform for automated testing
- **Spring Boot:** Java framework for building web applications
- **JWT:** JSON Web Token for authentication
- **REST API:** Representational State Transfer Application Programming Interface

---

## 20. UNIT TEST DESIGN & DEMO

### 20.1 Selected Method for Demo
**Method:** `VoucherService.isVoucherValid(Voucher voucher, LocalDateTime now)`

**Reason for Selection:**
- Clear business logic with multiple conditional branches
- Excellent candidate for both Black Box and White Box testing
- Critical functionality affecting payment processing
- Complex validation rules requiring comprehensive testing

### 20.2 Test Case Design

#### Black Box Testing - Equivalence Partitioning (EP)
| Test Case | Description | Input | Expected Output |
|-----------|-------------|-------|-----------------|
| EP-01 | Valid voucher | status=active, start < now < end, usedCount < maxUsage | true |
| EP-02 | Null voucher | voucher = null | false |
| EP-03 | Inactive status | status=inactive | false |
| EP-04 | Not started | start > now | false |
| EP-05 | Expired | end < now | false |
| EP-06 | Over usage limit | usedCount >= maxUsage | false |

#### Black Box Testing - Boundary Value Analysis (BVA)
| Test Case | Description | Input | Expected Output |
|-----------|-------------|-------|-----------------|
| BVA-01 | Start exactly at now | start = now | true |
| BVA-02 | End exactly at now | end = now | false |
| BVA-03 | Used count equals max | usedCount = maxUsage | false |
| BVA-04 | Used count one less than max | usedCount = maxUsage - 1 | true |
| BVA-05 | Unlimited usage | maxUsage = null | true |
| BVA-06 | No usage tracking | usedCount = null | true |

#### White Box Testing
- **Statement Coverage:** 100% - All code statements executed
- **Decision Coverage:** 100% - All conditional branches tested
- **Branch Coverage:** 100% - All possible execution paths covered

### 20.3 Demo Execution Steps

#### Step 1: Environment Setup
```bash
# Clean and compile project
mvn clean compile

# Compile test classes
mvn test-compile
```

#### Step 2: Execute Unit Tests
```bash
# Run VoucherService tests
mvn test -Dtest=VoucherServiceTest

# Run with coverage report
mvn test jacoco:report
```

#### Step 3: View Results
- **Terminal Output:** Real-time test execution results
- **Surefire Reports:** `target/surefire-reports/`
- **Coverage Report:** `target/site/jacoco/index.html`

### 20.4 Expected Demo Results
```
[INFO] Running com.group2.ADN.VoucherServiceTest
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS

Coverage Summary:
- Statement Coverage: 100%
- Decision Coverage: 100%
- Branch Coverage: 100%
```

### 20.5 Attachments
- `src/test/java/com/group2/ADN/VoucherServiceTest.java` - Complete test implementation
- `UNIT_TEST_DOCUMENTATION.md` - Detailed test case documentation
- `HDSD_UNIT_TEST_DEMO.md` - Step-by-step demo guide
- JaCoCo HTML coverage report
- Surefire test execution reports

---

**Document Version:** 1.0  
**Last Updated:** 2025-07-14  
**Next Review:** 2025-07-21 