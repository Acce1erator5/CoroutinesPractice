# Practice Work: Coroutines Introduction

## Difference Between Suspending and Blocking

```text
Suspending frees the current thread to perform other tasks while the coroutine is waiting (e.g., for a network response or a delay). The thread does not idle.
Blocking completely freezes the execution thread. The thread cannot perform any other work (other coroutines on this thread will be forced to wait).
```

---

## Answers to Questions and Program Outputs

### Question 1. First coroutine
**Output:**
```text
Order accepted
Coffee ready
```
**Explanation:** `launch` creates a new coroutine asynchronously and does not block the current thread. The main body of `runBlocking` continues execution immediately, printing "Order accepted", while the launched coroutine suspends for 1 second using `delay`.

### Question 2. Three baristas
**Output (with delay):**
```text
espresso start
tea start
latte start
tea end
latte end
espresso end
Time with delay: ~300 ms
```
**Output (with Thread.sleep):**
```text
espresso start
espresso end
tea start
tea end
latte start
latte end
Time with sleep: ~600 ms
```
**Explanation:**
When using `delay`, the thread is released whenever a coroutine suspends, allowing it to instantly switch to another task (multitasking). When using `Thread.sleep`, the thread is completely blocked by the active coroutine and cannot perform any other work until the sleep timer expires, making execution strictly sequential.

### Question 3. Chain of suspend functions
**Output:**
```text
Step 1: Load User
Step 2: Check Password
Step 3: Open Profile
```
**Explanation:** Suspending (`suspend`) pauses only the coroutine itself but preserves the sequential execution order of the code inside it. Lines are not reordered when called sequentially.

### Question 4. Calling suspend from normal code
**Compile Error Message:**
`Suspend function '...' should be called only from a coroutine or another suspend function`
**Android UI Thread Warning:** The fix using blocking `runBlocking` must never be used on the Android main (UI) thread, as it will freeze the interface rendering loop, leading to an application freeze (ANR — Application Not Responding).

### Question 5. Suspend is not a thread
**Output:**
```text
Thread before call: main
Thread returned from suspend: main
```
**Explanation:** The `suspend` modifier itself does not switch threads. The function executes on the exact same thread it was called from (in this case, `main`).
**Hidden Parameter:** The compiler adds a hidden `Continuation<T>` parameter to every suspend function to implement the Continuation Passing Style (CPS) mechanism.

### Question 6. Ten downloads, one thread
**Output:**
```text
download 0 started on main
...
download 9 started on main
download 0 finished
...
download 9 finished
```
**Concurrency vs Parallelism:** This is **Concurrency**, not parallelism. The tasks are executed interleaved on a single thread (`main`), switching between one another at suspension points. Parallelism is impossible within a single thread.

### Question 7. Order of launch
**Output:**
```text
1
4
2
5
3
```
**Explanation:** "2" does not immediately follow "1" because `launch` places the coroutine into the dispatcher's queue. The current coroutine continues executing its body, printing "4", and only when it hits its own suspension point (`delay(50)`), the dispatcher hands over control to the new coroutine, which then prints "2".

### Question 8. Parent waits for child
**Output:**
```text
parent body done
child done
report completed
```
**Explanation:** According to the rules of **Structured Concurrency**, a parent coroutine (or scope) cannot complete until all of its child coroutines have completely finished their execution.

### Question 9. Screen closed, requests cancelled
**Output:**
```text
child 1 cleanup done
child 2 cleanup done
end
```
**Explanation:** Cancelling a parent coroutine automatically propagates cancellation cascade-style down to all of its child coroutines.

### Question 10. Profile ready
**Output:**
```text
settings loaded
avatar loaded
Profile ready
end of main
detached coroutine done
```
**Explanation:** `coroutineScope` suspends the further execution of the current coroutine until all child tasks launched within its block are complete. A normal `launch` placed after the scope block creates an independent task and proceeds immediately, which is why "end of main" prints before "detached coroutine done".

### Question 11. Reusable counter
**Explanation:** The `CoroutineScope` receiver is required for the extension function to bind the lifecycle of the newly created coroutine (`launch`) to the context and scope of the caller.

### Question 12. Returns at once or waits
**Explanation:** The extension function `CoroutineScope.greetBackground` launches a background task asynchronously and returns control immediately. In contrast, `greetSeveralWait` uses `coroutineScope` inside a suspend function, forcing it to wait for all inner child coroutines to complete before returning.

### Question 13. Five builders
**Output:**
```text
launch builder returns: StandaloneCoroutine{Active}
async builder returns: DeferredCoroutine{Active}
coroutineScope returns: Scope block return value
```
**Cheat Sheet:**
* `launch`: Returns a `Job`, does not block the thread, waits for children.
* `async`: Returns a `Deferred<T>`, does not block the thread, waits for children.
* `runBlocking`: Returns `T` (lambda result), blocks the current thread, waits for children.
* `coroutineScope`: Returns `T` (lambda result), suspends the coroutine (without blocking), waits for children.
* `withContext`: Returns `T`, switches context/dispatcher, suspends the coroutine, waits for children.

### Question 14. Job or Deferred
**Explanation:** A `Job` represents a handle to an asynchronous task and does not produce a return value (returns `Unit`). A `Deferred` extends `Job` and holds a future result of a computation, which can be retrieved using the terminal `.await()` call.

### Question 15. Three exports in a scope
**Output:**
```text
Start
Y
Z
X
All done
```
