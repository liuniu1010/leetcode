import Twosum

inputList = [1,3,6,9,10]
target = 16
targetIndices = Twosum.getIndices(inputList, target)
if targetIndices == [2, 4]:
    print("Twosum test passed")
else:
    print("Twosum test failed!")
