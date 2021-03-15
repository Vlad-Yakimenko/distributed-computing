package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func setUpMatrix(n, m int) [][]bool {
	matrix := make([][]bool, n)
	for i := range matrix {
		matrix[i] = make([]bool, m)
	}

	// set the Bear position
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	var (
		x = r.Intn(n)
		y = r.Intn(m)
	)

	matrix[x][y] = true
	return matrix
}

func regionToString(region []bool) string {
	str := "["
	for i := range region {
		if region[i] {
			str += "1 "
		} else {
			str += "0 "
		}
	}
	str += "]"
	return str
}

func findBear(id int, waitGroup *sync.WaitGroup, regions <-chan []bool, bearFoundSignal chan bool) {
	defer waitGroup.Done()

	for region := range regions {
		select {
		case <-bearFoundSignal:
			bearFoundSignal <- true
			fmt.Println()
			return
		default:
			str := regionToString(region)
			fmt.Println("Bees group", id, "searching the bear in a region ", str)

			for index, i := range region {
				time.Sleep(time.Microsecond * 100)
				if i {
					fmt.Println("Bees group", id, "found the bear in a region", str, " on position", index)
					bearFoundSignal <- true
					fmt.Println("Bees group", id, "returned.")
					return
				}
			}
		}
		fmt.Println("Bees group", id, "returned.")
	}
}

func main() {
	const (
		//n is number of rows
		n = 10
		//n is number of rows
		m = 100
		//beesNum is number of bees
		beesNum = 4
	)
	forestMatrix := setUpMatrix(n, m)
	regions := make(chan []bool, n)
	bearFoundSignal := make(chan bool, 1)

	var waitGroup sync.WaitGroup

	for i := 0; i < beesNum; i++ {
		waitGroup.Add(1)
		go findBear(i, &waitGroup, regions, bearFoundSignal)
	}

	for i := 0; i < n; i++ {
		regions <- forestMatrix[i]
	}

	close(regions)
	waitGroup.Wait()
}
