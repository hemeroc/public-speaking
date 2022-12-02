package kotlinvienna

fun isPrime(primeCandidate: Int): Boolean {
    for (i in 2..primeCandidate / 2) {
        if (primeCandidate % i == 0) {
            return false
        }
    }
    return primeCandidate > 1
}


fun main() {
    val from = 0
    val to = 100_000
    val primeNumbers = (from..to)
        .filter { isPrime(it) }
    println("Found ${primeNumbers.size} from $from to $to.")
}
