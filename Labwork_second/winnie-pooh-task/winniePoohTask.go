package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func createForest(rows, columns int) [][]bool {
	matrix := make([][]bool, rows)
	for i := range matrix {
		matrix[i] = make([]bool, columns)
	}

	// set the Bear position
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	var (
		x = r.Intn(rows)
		y = r.Intn(columns)
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

func findBear(id int, waitGroup *sync.WaitGroup, regions <-chan []bool, bearFound chan bool) {
	defer waitGroup.Done()

	for region := range regions {
		select {
		case <-bearFound:
			bearFound <- true
			fmt.Println()
			return

		default:
			str := regionToString(region)
			fmt.Println("Bees group", id, "searching the bear in a region ", str)

			for index, i := range region {
				time.Sleep(time.Microsecond * 100)
				if i {
					fmt.Println("Bees group", id, "found the bear in a region", str, " on position", index)
					bearFound <- true
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
		rows            = 10
		columns         = 100
		beeGroupsAmount = 4
	)
	forest := createForest(rows, columns)
	regions := make(chan []bool, rows)
	bearFound := make(chan bool, 1)

	var waitGroup sync.WaitGroup

	for i := 0; i < beeGroupsAmount; i++ {
		waitGroup.Add(1)
		go findBear(i, &waitGroup, regions, bearFound)
	}

	for i := 0; i < rows; i++ {
		regions <- forest[i]
	}

	close(regions)
	waitGroup.Wait()
}
