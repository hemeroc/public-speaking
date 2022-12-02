package kotlinvienna

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PrimeCheckerTest {

    @Test
    fun testIsPrime0() {
        assertFalse(isPrime(0))
    }

    @Test
    fun testIsPrime1() {
        assertFalse(isPrime(1))
    }

    @Test
    fun testIsPrime2() {
        assertTrue(isPrime(2))
    }
}
