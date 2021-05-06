package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var random = rand.New(rand.NewSource(time.Now().UnixNano()))

// CyclicBarrier : implements a synchronization construct of cyclic barrier
type CyclicBarrier struct {
	generation  int
	count       int
	parties     int
	finishState chan bool
	trip        *sync.Cond
	condition   *Condition
}

func (b *CyclicBarrier) nextGeneration() {
	fmt.Println()
	state := (*b.condition).check()

	for i := 0; i < b.parties; i++ {
		b.finishState <- state
	}

	b.trip.Broadcast()
	b.count = b.parties
	b.generation++
}

// Await : called by a thread that needs to wait until other threads reach barrier
func (b *CyclicBarrier) Await() {
	b.trip.L.Lock()
	defer b.trip.L.Unlock()

	generation := b.generation

	b.count--
	index := b.count

	if index == 0 {
		b.nextGeneration()
	} else {
		for generation == b.generation {
			//wait for current generation complete
			b.trip.Wait()
		}
	}
}

//NewCyclicBarrier : create new cyclic barrier
func NewCyclicBarrier(num int, condition *Condition) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = num
	b.parties = num
	b.trip = sync.NewCond(&sync.Mutex{})
	b.finishState = make(chan bool, 3)
	b.condition = condition

	return &b
}

func arrayModifier(id int, array *[]int, barrier *CyclicBarrier, waitGroup *sync.WaitGroup) {
	defer (*waitGroup).Done()
	for {
		element := random.Intn(len(*array))

		if random.Intn(2)%2 == 0 && (*array)[element] > 0 {
			(*array)[element]--
		} else if (*array)[element] < 10 {
			(*array)[element]++
		}

		fmt.Println("Thread #", id, "modified array:", (*array), "and reached barrier")
		barrier.Await()
		finish := <-barrier.finishState

		if finish {
			fmt.Println("Thread #", id, "finished")
			break
		}
	}
}

func generateArray(size int) []int {
	array := make([]int, size)

	for i := range array {
		array[i] = random.Intn(10)
	}

	return array
}

//Condition to check when barrier is reached
type Condition struct {
	array1 *[]int
	array2 *[]int
	array3 *[]int
}

//NewCondition retuns instance of condition
func NewCondition(array1, array2, array3 *[]int) *Condition {
	c := Condition{}
	c.array1 = array1
	c.array2 = array2
	c.array3 = array3

	return &c
}

func (b *Condition) check() bool {
	sum1 := getSum(b.array1)
	sum2 := getSum(b.array2)
	sum3 := getSum(b.array3)

	if sum1 == sum2 && sum2 == sum3 {
		return true
	}

	return false
}

func getSum(array *[]int) int {
	sum := 0

	for i := range *array {
		sum += (*array)[i]
	}

	return sum
}

func main() {
	waitGroup := sync.WaitGroup{}
	array1 := generateArray(5)
	fmt.Println("Array 1:", array1)
	array2 := generateArray(5)
	fmt.Println("Array 2:", array2)
	array3 := generateArray(5)
	fmt.Println("Array 3:", array3)

	condition := NewCondition(&array1, &array2, &array3)

	barrier := NewCyclicBarrier(3, condition)

	waitGroup.Add(3)
	go arrayModifier(1, &array1, barrier, &waitGroup)
	go arrayModifier(2, &array2, barrier, &waitGroup)
	go arrayModifier(3, &array3, barrier, &waitGroup)

	waitGroup.Wait()
}
