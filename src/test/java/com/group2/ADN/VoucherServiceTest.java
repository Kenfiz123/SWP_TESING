package com.group2.ADN;

import com.group2.ADN.entity.Voucher;
import com.group2.ADN.repository.VoucherRepository;
import com.group2.ADN.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for VoucherService
 * 
 * Test Strategy:
 * 1. Black Box Testing:
 *    - Equivalence Partitioning (EP): Grouping inputs into valid/invalid categories
 *    - Boundary Value Analysis (BVA): Testing boundary conditions
 * 
 * 2. White Box Testing:
 *    - Statement Coverage: Every statement executed at least once
 *    - Decision Coverage: Every decision point tested for both true/false outcomes
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    private Voucher validVoucher;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        
        // Create a valid voucher for testing
        validVoucher = new Voucher();
        validVoucher.setId(1);
        validVoucher.setName("TEST10");
        validVoucher.setType("percent");
        validVoucher.setValue(new BigDecimal("10"));
        validVoucher.setStart(now.minusDays(1));  // Started yesterday
        validVoucher.setEndDate(now.plusDays(5)); // Ends in 5 days
        validVoucher.setStatus("active");
        validVoucher.setMaxUsage(100);
        validVoucher.setUsedCount(50);
        validVoucher.setCreatedAt(now.minusDays(2));
        validVoucher.setUpdatedAt(now.minusDays(1));
    }

    // ========================================
    // BLACK BOX TESTING - Equivalence Partitioning
    // ========================================

    @Test
    @DisplayName("EP-01: Valid voucher with all conditions met")
    void testIsVoucherValid_ValidVoucher_AllConditionsMet() {
        // Arrange: Valid voucher with all conditions satisfied
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(100);
        voucher.setUsedCount(50);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertTrue(result, "Valid voucher should return true");
    }

    @Test
    @DisplayName("EP-02: Null voucher - invalid input")
    void testIsVoucherValid_NullVoucher_ReturnsFalse() {
        // Act
        boolean result = voucherService.isVoucherValid(null, now);

        // Assert
        assertFalse(result, "Null voucher should return false");
    }

    @Test
    @DisplayName("EP-03: Inactive voucher status")
    void testIsVoucherValid_InactiveStatus_ReturnsFalse() {
        // Arrange: Voucher with inactive status
        Voucher voucher = new Voucher();
        voucher.setStatus("inactive");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Inactive voucher should return false");
    }

    @Test
    @DisplayName("EP-04: Voucher not yet started")
    void testIsVoucherValid_NotYetStarted_ReturnsFalse() {
        // Arrange: Voucher that starts in the future
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.plusDays(1));
        voucher.setEndDate(now.plusDays(5));

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Future voucher should return false");
    }

    @Test
    @DisplayName("EP-05: Expired voucher")
    void testIsVoucherValid_ExpiredVoucher_ReturnsFalse() {
        // Arrange: Voucher that has expired
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(5));
        voucher.setEndDate(now.minusDays(1));

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Expired voucher should return false");
    }

    @Test
    @DisplayName("EP-06: Voucher usage limit exceeded")
    void testIsVoucherValid_UsageLimitExceeded_ReturnsFalse() {
        // Arrange: Voucher with exceeded usage limit
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(10);
        voucher.setUsedCount(10);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Voucher with exceeded usage should return false");
    }

    // ========================================
    // BLACK BOX TESTING - Boundary Value Analysis
    // ========================================

    @Test
    @DisplayName("BVA-01: Voucher starts exactly at current time")
    void testIsVoucherValid_StartsExactlyNow_ReturnsTrue() {
        // Arrange: Voucher starts exactly at current time
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now);
        voucher.setEndDate(now.plusDays(1));

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertTrue(result, "Voucher starting exactly now should be valid");
    }

    @Test
    @DisplayName("BVA-02: Voucher ends exactly at current time")
    void testIsVoucherValid_EndsExactlyNow_ReturnsFalse() {
        // Arrange: Voucher ends exactly at current time
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Voucher ending exactly now should be invalid");
    }

    @Test
    @DisplayName("BVA-03: Voucher usage count equals max usage")
    void testIsVoucherValid_UsageEqualsMaxUsage_ReturnsFalse() {
        // Arrange: Voucher with usage count equal to max usage
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(100);
        voucher.setUsedCount(100);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertFalse(result, "Voucher with usage equal to max should be invalid");
    }

    @Test
    @DisplayName("BVA-04: Voucher usage count one less than max usage")
    void testIsVoucherValid_UsageOneLessThanMax_ReturnsTrue() {
        // Arrange: Voucher with usage count one less than max usage
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(100);
        voucher.setUsedCount(99);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertTrue(result, "Voucher with usage one less than max should be valid");
    }

    @Test
    @DisplayName("BVA-05: Null max usage (unlimited)")
    void testIsVoucherValid_NullMaxUsage_ReturnsTrue() {
        // Arrange: Voucher with null max usage (unlimited)
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(null);
        voucher.setUsedCount(1000);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertTrue(result, "Voucher with null max usage should be valid");
    }

    @Test
    @DisplayName("BVA-06: Null used count")
    void testIsVoucherValid_NullUsedCount_ReturnsTrue() {
        // Arrange: Voucher with null used count
        Voucher voucher = new Voucher();
        voucher.setStatus("active");
        voucher.setStart(now.minusDays(1));
        voucher.setEndDate(now.plusDays(1));
        voucher.setMaxUsage(100);
        voucher.setUsedCount(null);

        // Act
        boolean result = voucherService.isVoucherValid(voucher, now);

        // Assert
        assertTrue(result, "Voucher with null used count should be valid");
    }

    // ========================================
    // WHITE BOX TESTING - Statement Coverage
    // ========================================

    @Test
    @DisplayName("Statement Coverage: All statements executed")
    void testIsVoucherValid_StatementCoverage_AllStatementsExecuted() {
        // This test ensures all statements in isVoucherValid method are executed
        // by testing different combinations of conditions

        // Test 1: Null voucher
        assertFalse(voucherService.isVoucherValid(null, now));

        // Test 2: Inactive status
        Voucher inactiveVoucher = new Voucher();
        inactiveVoucher.setStatus("inactive");
        assertFalse(voucherService.isVoucherValid(inactiveVoucher, now));

        // Test 3: Future start date
        Voucher futureVoucher = new Voucher();
        futureVoucher.setStatus("active");
        futureVoucher.setStart(now.plusDays(1));
        futureVoucher.setEndDate(now.plusDays(5));
        assertFalse(voucherService.isVoucherValid(futureVoucher, now));

        // Test 4: Past end date
        Voucher expiredVoucher = new Voucher();
        expiredVoucher.setStatus("active");
        expiredVoucher.setStart(now.minusDays(5));
        expiredVoucher.setEndDate(now.minusDays(1));
        assertFalse(voucherService.isVoucherValid(expiredVoucher, now));

        // Test 5: Usage limit exceeded
        Voucher exceededVoucher = new Voucher();
        exceededVoucher.setStatus("active");
        exceededVoucher.setStart(now.minusDays(1));
        exceededVoucher.setEndDate(now.plusDays(1));
        exceededVoucher.setMaxUsage(10);
        exceededVoucher.setUsedCount(10);
        assertFalse(voucherService.isVoucherValid(exceededVoucher, now));

        // Test 6: Valid voucher
        Voucher validVoucher = new Voucher();
        validVoucher.setStatus("active");
        validVoucher.setStart(now.minusDays(1));
        validVoucher.setEndDate(now.plusDays(1));
        validVoucher.setMaxUsage(100);
        validVoucher.setUsedCount(50);
        assertTrue(voucherService.isVoucherValid(validVoucher, now));
    }

    // ========================================
    // WHITE BOX TESTING - Decision Coverage
    // ========================================

    @Test
    @DisplayName("Decision Coverage: All decision points tested")
    void testIsVoucherValid_DecisionCoverage_AllDecisionsTested() {
        // Test all decision points in the method:
        // 1. voucher == null (true/false)
        // 2. !"active".equals(voucher.getStatus()) (true/false)
        // 3. voucher.getStart().isAfter(now) (true/false)
        // 4. voucher.getEndDate().isBefore(now) (true/false)
        // 5. voucher.getMaxUsage() != null (true/false)
        // 6. voucher.getUsedCount() != null (true/false)
        // 7. voucher.getUsedCount() >= voucher.getMaxUsage() (true/false)

        // Decision 1: voucher == null (true)
        assertFalse(voucherService.isVoucherValid(null, now));

        // Decision 1: voucher == null (false) + Decision 2: status != "active" (true)
        Voucher inactiveVoucher = new Voucher();
        inactiveVoucher.setStatus("inactive");
        assertFalse(voucherService.isVoucherValid(inactiveVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (true)
        Voucher futureVoucher = new Voucher();
        futureVoucher.setStatus("active");
        futureVoucher.setStart(now.plusDays(1));
        futureVoucher.setEndDate(now.plusDays(5));
        assertFalse(voucherService.isVoucherValid(futureVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (false) + Decision 4: endDate.isBefore(now) (true)
        Voucher expiredVoucher = new Voucher();
        expiredVoucher.setStatus("active");
        expiredVoucher.setStart(now.minusDays(5));
        expiredVoucher.setEndDate(now.minusDays(1));
        assertFalse(voucherService.isVoucherValid(expiredVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (false) + Decision 4: endDate.isBefore(now) (false) + Decision 5: maxUsage != null (true) + Decision 6: usedCount != null (true) + Decision 7: usedCount >= maxUsage (true)
        Voucher exceededVoucher = new Voucher();
        exceededVoucher.setStatus("active");
        exceededVoucher.setStart(now.minusDays(1));
        exceededVoucher.setEndDate(now.plusDays(1));
        exceededVoucher.setMaxUsage(10);
        exceededVoucher.setUsedCount(10);
        assertFalse(voucherService.isVoucherValid(exceededVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (false) + Decision 4: endDate.isBefore(now) (false) + Decision 5: maxUsage != null (false)
        Voucher unlimitedVoucher = new Voucher();
        unlimitedVoucher.setStatus("active");
        unlimitedVoucher.setStart(now.minusDays(1));
        unlimitedVoucher.setEndDate(now.plusDays(1));
        unlimitedVoucher.setMaxUsage(null);
        assertTrue(voucherService.isVoucherValid(unlimitedVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (false) + Decision 4: endDate.isBefore(now) (false) + Decision 5: maxUsage != null (true) + Decision 6: usedCount != null (false)
        Voucher nullUsedCountVoucher = new Voucher();
        nullUsedCountVoucher.setStatus("active");
        nullUsedCountVoucher.setStart(now.minusDays(1));
        nullUsedCountVoucher.setEndDate(now.plusDays(1));
        nullUsedCountVoucher.setMaxUsage(100);
        nullUsedCountVoucher.setUsedCount(null);
        assertTrue(voucherService.isVoucherValid(nullUsedCountVoucher, now));

        // Decision 1: voucher == null (false) + Decision 2: status == "active" (true) + Decision 3: start.isAfter(now) (false) + Decision 4: endDate.isBefore(now) (false) + Decision 5: maxUsage != null (true) + Decision 6: usedCount != null (true) + Decision 7: usedCount >= maxUsage (false)
        Voucher validVoucher = new Voucher();
        validVoucher.setStatus("active");
        validVoucher.setStart(now.minusDays(1));
        validVoucher.setEndDate(now.plusDays(1));
        validVoucher.setMaxUsage(100);
        validVoucher.setUsedCount(50);
        assertTrue(voucherService.isVoucherValid(validVoucher, now));
    }

    // ========================================
    // INTEGRATION TESTS - Repository Interaction
    // ========================================

    @Test
    @DisplayName("Integration: Create voucher with repository")
    void testCreateVoucher_WithRepository() {
        // Arrange
        Voucher voucherToCreate = new Voucher();
        voucherToCreate.setName("NEW10");
        voucherToCreate.setType("percent");
        voucherToCreate.setValue(new BigDecimal("10"));
        voucherToCreate.setStart(now.minusDays(1));
        voucherToCreate.setEndDate(now.plusDays(5));

        when(voucherRepository.save(any(Voucher.class))).thenReturn(validVoucher);

        // Act
        Voucher result = voucherService.createVoucher(voucherToCreate);

        // Assert
        assertNotNull(result);
        assertEquals("TEST10", result.getName());
        assertEquals("active", result.getStatus());
        assertEquals(0, result.getUsedCount());
        verify(voucherRepository).save(any(Voucher.class));
    }

    @Test
    @DisplayName("Integration: Get voucher by ID")
    void testGetVoucherById_WithRepository() {
        // Arrange
        when(voucherRepository.findById(1)).thenReturn(Optional.of(validVoucher));

        // Act
        Optional<Voucher> result = voucherService.getVoucherById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("TEST10", result.get().getName());
        verify(voucherRepository).findById(1);
    }

    @Test
    @DisplayName("Integration: Get active vouchers")
    void testGetActiveVouchers_WithRepository() {
        // Arrange
        List<Voucher> activeVouchers = Arrays.asList(validVoucher);
        when(voucherRepository.findByStatusAndStartLessThanEqualAndEndDateGreaterThanEqual("active", now, now))
                .thenReturn(activeVouchers);

        // Act
        List<Voucher> result = voucherService.getActiveVouchers(now);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TEST10", result.get(0).getName());
        verify(voucherRepository).findByStatusAndStartLessThanEqualAndEndDateGreaterThanEqual("active", now, now);
    }

    // ========================================
    // ERROR HANDLING TESTS
    // ========================================

    @Test
    @DisplayName("Error Handling: Update non-existent voucher")
    void testUpdateVoucher_NonExistentVoucher_ReturnsNull() {
        // Arrange
        when(voucherRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Voucher result = voucherService.updateVoucher(999, validVoucher);

        // Assert
        assertNull(result);
        verify(voucherRepository).findById(999);
        verify(voucherRepository, never()).save(any(Voucher.class));
    }

    @Test
    @DisplayName("Error Handling: Delete non-existent voucher")
    void testDeleteVoucher_NonExistentVoucher_ReturnsFalse() {
        // Arrange
        when(voucherRepository.existsById(999)).thenReturn(false);

        // Act
        boolean result = voucherService.deleteVoucher(999);

        // Assert
        assertFalse(result);
        verify(voucherRepository).existsById(999);
        verify(voucherRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Error Handling: Extend non-existent voucher")
    void testExtendVoucher_NonExistentVoucher_ReturnsNull() {
        // Arrange
        when(voucherRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Voucher result = voucherService.extendVoucher(999, now.plusDays(10));

        // Assert
        assertNull(result);
        verify(voucherRepository).findById(999);
        verify(voucherRepository, never()).save(any(Voucher.class));
    }
} 