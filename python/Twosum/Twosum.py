#
#  source: https://oj.leetcode.com/problemset/algorithms/
#  Author: Liu, Niu <liuniu@tsinghua.org.cn>
#

#
#  Given an array of integers, find two numbers such that they add up to a specific target number.
#  The function twoSum should return indices of the two numbers such that they add up to the target, 
#  where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are zero-based.
#  You may assume that each input would have exactly one solution.
#  Input: numbers={2, 7, 11, 15}, target=9
#  Output: index1=0, index2=1
#
#  @author liuniu
#

#  search the two indices, the running time should be O(size of input)
def getIndices(inputList, target):
    dictOfInput = {}
    index = 0
    #
    #  first loop through the input, record the value and index into cache
    #  to make sure the overall loop times is O(input.size), not O(input.size^2)
    #
    for intnumber in inputList:
        dictOfInput[intnumber] = index
        index += 1

    targetIndices = []
    index = 0
    for intnumber in inputList:
        targetNumber = target - intnumber
        if targetNumber in dictOfInput:
            targetIndices = [index, dictOfInput.get(targetNumber)]
            targetIndices.sort()
            return targetIndices

        index += 1

    return None
