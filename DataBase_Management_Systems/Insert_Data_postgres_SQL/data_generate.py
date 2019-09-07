from xlrd import open_workbook
from xlwt import Workbook
import json,urllib.request
import math
import xlsxwriter								#NAME OF INPUT FILE CONTAINING ONE SHEET
import re	
workbook = xlsxwriter.Workbook('data.xlsx')										#NAME OF GENERATED FILE
worksheet = workbook.add_worksheet()

#starting point																			#DO ENTRY IN ROW WISE
for i in range(0,500000):
        print(i)
        student='2015MT'+str(i%5000+1)
        course='MTL'+str(i//5000+1)
        worksheet.write(i+1,0,student)
        worksheet.write(i+1,1,course)



workbook.close()
