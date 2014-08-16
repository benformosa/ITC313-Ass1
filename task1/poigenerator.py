#!/usr/bin/python
import sys, argparse, random

def main(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument('-f', '--float', action='store_true')
    parser.add_argument('-p', '--points', default=10)
    parser.add_argument('-t', '--types', default=5)
    parser.add_argument('-m', '--max', default=100)
    args = parser.parse_args()

    for i in range(0, int(args.points)):
        point = str(random.randint(1, int(args.types))) + ','
        if args.float:
            point += "{:0.2f}".format(random.uniform(0, int(args.max))) + ','
            point += "{:0.2f}".format(random.uniform(0, int(args.max)))
        else:
            point += str(random.randint(0, int(args.max))) + ','
            point += str(random.randint(0, int(args.max)))

        print(point)

if __name__ == "__main__":
    main(sys.argv[1:])
