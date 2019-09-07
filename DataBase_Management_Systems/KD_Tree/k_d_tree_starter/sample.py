import sys

if __name__ == '__main__':

	dataset_file = sys.argv[1]

	# [TODO] Construct kdTree using dataset_file here

	# Request name/path of query_file from parent by just sending "0" on stdout
	sys.stdout.write('0\n')

	# Wait till the parent responds with name/path of query_file and k | Timer will start now
	query_file = input()
	k = int(input())

	# [TODO] Read the query point from query_file, do kNN using the kdTree and output the answer to results.txt

	# Convey to parent that results.txt is ready by sending "1" on stdout | Timer will stop now and this process will be killed
	sys.stdout.write('1\n')
