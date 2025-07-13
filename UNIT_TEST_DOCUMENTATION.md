# Unit Testing Documentation - VoucherService

## 1. Tổng quan về Component được kiểm thử

### Component: `VoucherService.isVoucherValid()`
- **Vị trí**: `src/main/java/com/group2/ADN/service/VoucherService.java`
- **Mục đích**: Kiểm tra tính hợp lệ của voucher dựa trên các điều kiện nghiệp vụ
- **Input**: Voucher object và thời gian hiện tại
- **Output**: Boolean (true/false)

### Logic nghiệp vụ:
```java
public boolean isVoucherValid(Voucher voucher, LocalDateTime now) {
    if (voucher == null) return false;
    if (!"active".equals(voucher.getStatus())) return false;
    if (voucher.getStart().isAfter(now) || voucher.getEndDate().isBefore(now)) return false;
    if (voucher.getMaxUsage() != null && voucher.getUsedCount() != null && voucher.getUsedCount() >= voucher.getMaxUsage()) return false;
    return true;
}
```

## 2. Thiết kế Test Cases

### 2.1 Black Box Testing - Equivalence Partitioning (EP)

#### Input Domain Analysis:
- **voucher**: null | valid Voucher object
- **voucher.getStatus()**: "active" | "inactive" | "expired" | null
- **voucher.getStart()**: past | present | future
- **voucher.getEndDate()**: past | present | future
- **voucher.getMaxUsage()**: null | positive integer
- **voucher.getUsedCount()**: null | positive integer
- **now**: current timestamp

#### Test Cases EP:

| Test Case | Input | Expected Output | Partition |
|-----------|-------|-----------------|-----------|
| EP-01 | Valid voucher, all conditions met | true | Valid input partition |
| EP-02 | null voucher | false | Invalid input partition |
| EP-03 | Inactive status | false | Invalid status partition |
| EP-04 | Future start date | false | Invalid time partition |
| EP-05 | Past end date | false | Invalid time partition |
| EP-06 | Usage limit exceeded | false | Invalid usage partition |

### 2.2 Black Box Testing - Boundary Value Analysis (BVA)

#### Boundary Conditions:
- **Start time**: exactly at current time
- **End time**: exactly at current time
- **Usage count**: equals max usage, one less than max usage
- **Null values**: max usage null, used count null

#### Test Cases BVA:

| Test Case | Input | Expected Output | Boundary |
|-----------|-------|-----------------|----------|
| BVA-01 | Start exactly at now | true | Boundary: start = now |
| BVA-02 | End exactly at now | false | Boundary: end = now |
| BVA-03 | Used count = max usage | false | Boundary: used = max |
| BVA-04 | Used count = max usage - 1 | true | Boundary: used < max |
| BVA-05 | Max usage = null | true | Boundary: unlimited usage |
| BVA-06 | Used count = null | true | Boundary: no usage tracking |

### 2.3 White Box Testing - Statement Coverage

#### Statements to cover:
1. `if (voucher == null) return false;`
2. `if (!"active".equals(voucher.getStatus())) return false;`
3. `if (voucher.getStart().isAfter(now) || voucher.getEndDate().isBefore(now)) return false;`
4. `if (voucher.getMaxUsage() != null && voucher.getUsedCount() != null && voucher.getUsedCount() >= voucher.getMaxUsage()) return false;`
5. `return true;`

#### Test Cases for Statement Coverage:
- Test 1: Null voucher (covers statement 1)
- Test 2: Inactive status (covers statements 1, 2)
- Test 3: Future start date (covers statements 1, 2, 3)
- Test 4: Past end date (covers statements 1, 2, 3)
- Test 5: Usage limit exceeded (covers statements 1, 2, 3, 4)
- Test 6: Valid voucher (covers all statements including 5)

### 2.4 White Box Testing - Decision Coverage

#### Decision Points:
1. `voucher == null` (true/false)
2. `!"active".equals(voucher.getStatus())` (true/false)
3. `voucher.getStart().isAfter(now)` (true/false)
4. `voucher.getEndDate().isBefore(now)` (true/false)
5. `voucher.getMaxUsage() != null` (true/false)
6. `voucher.getUsedCount() != null` (true/false)
7. `voucher.getUsedCount() >= voucher.getMaxUsage()` (true/false)

#### Test Cases for Decision Coverage:
- **Decision 1 (true)**: null voucher
- **Decision 1 (false) + Decision 2 (true)**: inactive status
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (true)**: future start
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (false) + Decision 4 (true)**: past end
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (false) + Decision 4 (false) + Decision 5 (true) + Decision 6 (true) + Decision 7 (true)**: usage exceeded
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (false) + Decision 4 (false) + Decision 5 (false)**: unlimited usage
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (false) + Decision 4 (false) + Decision 5 (true) + Decision 6 (false)**: null used count
- **Decision 1 (false) + Decision 2 (true) + Decision 3 (false) + Decision 4 (false) + Decision 5 (true) + Decision 6 (true) + Decision 7 (false)**: valid voucher

## 3. Demo Code và Kết quả

### 3.1 Chạy Unit Tests

```bash
# Chạy tất cả tests
mvn test -Dtest=VoucherServiceTest

# Chạy tests theo category
mvn test -Dtest=VoucherServiceTest#testIsVoucherValid_ValidVoucher_AllConditionsMet
```

### 3.2 Kết quả mong đợi:

```
[INFO] Running com.group2.ADN.VoucherServiceTest
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-surefire-plugin:3.0.0-M5:test (test) @ ADN ---
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
```

### 3.3 Coverage Report:

```
Statement Coverage: 100%
Decision Coverage: 100%
Branch Coverage: 100%
```

## 4. Nội dung trình bày (8 phút)

### 4.1 Giới thiệu (1 phút)
- **Component được test**: VoucherService.isVoucherValid()
- **Mục đích**: Kiểm tra tính hợp lệ của voucher
- **Phương pháp test**: Black Box (EP, BVA) + White Box (Statement, Decision)

### 4.2 Black Box Testing (3 phút)

#### Equivalence Partitioning (1.5 phút)
- **Input domains**: voucher, status, start/end time, usage limits
- **Test cases**: 6 test cases covering valid/invalid partitions
- **Demo**: Chạy EP test cases

#### Boundary Value Analysis (1.5 phút)
- **Boundary conditions**: exact time matches, usage limits
- **Test cases**: 6 test cases covering boundary values
- **Demo**: Chạy BVA test cases

### 4.3 White Box Testing (3 phút)

#### Statement Coverage (1.5 phút)
- **Mục tiêu**: Mọi statement được thực thi ít nhất 1 lần
- **Test cases**: 6 test cases covering all statements
- **Demo**: Chạy statement coverage tests

#### Decision Coverage (1.5 phút)
- **Mục tiêu**: Mọi decision point được test true/false
- **Test cases**: 8 test cases covering all decisions
- **Demo**: Chạy decision coverage tests

### 4.4 Kết quả và Kết luận (1 phút)
- **Coverage**: 100% statement và decision coverage
- **Số lượng test cases**: 18 test cases
- **Kết quả**: Tất cả tests pass
- **Đánh giá**: Component được test đầy đủ và đáng tin cậy

## 5. Files liên quan

1. **Test Class**: `src/test/java/com/group2/ADN/VoucherServiceTest.java`
2. **Test Config**: `src/test/resources/application-test.properties`
3. **Source Code**: `src/main/java/com/group2/ADN/service/VoucherService.java`
4. **Entity**: `src/main/java/com/group2/ADN/entity/Voucher.java`

## 6. Hướng dẫn chạy Demo

### 6.1 Chuẩn bị:
```bash
# Đảm bảo Maven và Java đã cài đặt
mvn --version
java --version

# Clean và compile project
mvn clean compile
```

### 6.2 Chạy tests:
```bash
# Chạy tất cả tests
mvn test

# Chạy chỉ VoucherServiceTest
mvn test -Dtest=VoucherServiceTest

# Chạy với coverage report
mvn test jacoco:report
```

### 6.3 Xem kết quả:
- Test results: `target/surefire-reports/`
- Coverage report: `target/site/jacoco/`

## 7. Đánh giá và Cải tiến

### 7.1 Điểm mạnh:
- Coverage đầy đủ (100% statement và decision)
- Test cases có tính hệ thống và logic
- Sử dụng Mockito để isolate unit tests
- Documentation chi tiết

### 7.2 Cải tiến có thể:
- Thêm mutation testing
- Thêm performance testing
- Thêm integration testing với database
- Thêm stress testing với large datasets 