# coding = utf-8
import os
import shutil


def main(path):
    fileList = os.listdir(path)
    for fileName in fileList:
        filePath = os.path.join(path,fileName)
        if os.path.isdir(filePath):
            if fileName == 'build':
                shutil.rmtree(filePath, True)
                print('delete:' + filePath)
            else:
                main(filePath)


main('.')
