# HƯỚNG DẪN CHI TIẾT UNIT TEST – THIẾT KẾ & DEMO CODE

**Nhóm thực hiện: Kenfi, Duy**

---

## 1. Lựa chọn thành phần kiểm thử
- **Chọn method:** `VoucherService.isVoucherValid(Voucher voucher, LocalDateTime now)`
- **Lý do chọn:** Hàm này có logic nghiệp vụ rõ ràng, nhiều nhánh điều kiện, phù hợp kiểm thử Black Box & White Box.

---

## 2. Thiết kế test case

### 2.1. Black Box Testing
#### a. Equivalence Partitioning (EP)
| Test Case | Mô tả | Input | Kết quả |
|-----------|-------|-------|---------|
| EP-01 | Hợp lệ | status=active, start < now < end, usedCount < maxUsage | true |
| EP-02 | Null voucher | voucher = null | false |
| EP-03 | Không hoạt động | status=inactive | false |
| EP-04 | Chưa bắt đầu | start > now | false |
| EP-05 | Hết hạn | end < now | false |
| EP-06 | Vượt lượt dùng | usedCount >= maxUsage | false |

#### b. Boundary Value Analysis (BVA)
| Test Case | Mô tả | Input | Kết quả |
|-----------|-------|-------|---------|
| BVA-01 | Bắt đầu đúng lúc | start = now | true |
| BVA-02 | Kết thúc đúng lúc | end = now | false |
| BVA-03 | usedCount = maxUsage | usedCount = maxUsage | false |
| BVA-04 | usedCount = maxUsage - 1 | usedCount = maxUsage - 1 | true |
| BVA-05 | Không giới hạn lượt | maxUsage = null | true |
| BVA-06 | Chưa có lượt dùng | usedCount = null | true |

### 2.2. White Box Testing
- **Statement Coverage:** Đảm bảo mọi dòng lệnh trong hàm đều được thực thi.
- **Decision Coverage:** Đảm bảo mọi nhánh điều kiện (if/else) đều được kiểm tra true/false.

---

## 3. Demo code minh họa test case

### 3.1. Ví dụ mã test tiêu biểu
```java
@Test
@DisplayName("EP-01: Valid voucher with all conditions met")
void testIsVoucherValid_ValidVoucher_AllConditionsMet() {
    Voucher voucher = new Voucher();
    voucher.setStatus("active");
    voucher.setStart(now.minusDays(1));
    voucher.setEndDate(now.plusDays(1));
    voucher.setMaxUsage(100);
    voucher.setUsedCount(50);

    boolean result = voucherService.isVoucherValid(voucher, now);
    assertTrue(result, "Valid voucher should return true");
}
```

### 3.2. Chạy demo code
**Bước 1:** Clean & compile project
```bash
mvn clean compile
```
**Bước 2:** Biên dịch test
```bash
mvn test-compile
```
**Bước 3:** Chạy toàn bộ test case cho VoucherService
```bash
mvn test -Dtest=VoucherServiceTest
```
**Bước 4:** Xem kết quả trên terminal hoặc file `target/surefire-reports/`

### 3.3. Kết quả demo mẫu
```
[INFO] Running com.group2.ADN.VoucherServiceTest
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
- Nếu dùng Jacoco, xem coverage tại `target/site/jacoco/index.html` (coverage 100%).

---

## 4. Nội dung trình bày 8 phút

### 4.1. Giới thiệu (1 phút)
- Thành phần kiểm thử: VoucherService.isVoucherValid()
- Mục tiêu: kiểm tra logic nghiệp vụ voucher
- Phương pháp: Black Box (EP, BVA), White Box (Statement, Decision)

### 4.2. Trình bày test case (3 phút)
- Bảng test case EP, BVA (giải thích input, output)
- Ý nghĩa từng nhóm test

### 4.3. Demo code & kết quả (3 phút)
- Chạy lệnh test, show kết quả pass/fail
- Giải thích coverage đạt được

### 4.4. Kết luận (1 phút)
- Đã kiểm thử đầy đủ các trường hợp hợp lệ, biên, bất thường
- Kết quả: tất cả test pass, coverage 100%
- Đảm bảo logic nghiệp vụ voucher hoạt động đúng, tin cậy

---

## 5. Tài liệu & file liên quan
- `src/test/java/com/group2/ADN/VoucherServiceTest.java`: mã test chi tiết
- `src/main/java/com/group2/ADN/service/VoucherService.java`: code kiểm thử
- `UNIT_TEST_DOCUMENTATION.md`: tài liệu chi tiết test case
- `target/surefire-reports/`: kết quả test
- `target/site/jacoco/`: báo cáo coverage

---

**Nếu cần trình bày slide, chỉ cần copy bảng test case, mã test tiêu biểu, ảnh chụp kết quả test và coverage vào slide!** 