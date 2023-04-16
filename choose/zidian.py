import os
import re
desktop_path = os.path.join(os.path.expanduser("~"), "Desktop")
dot_file = os.path.join(desktop_path, "0-ast.dot")
# 读取文件内容
with open(dot_file, 'r') as f:
    content = f.read()
# 使用正则表达式提取数字和字符串
numbers = re.findall(r'"(\d+)"', content)
strings = re.findall(r',([^,]*)', content)
# 将数字和字符串逐行写入文件中
output_file = os.path.join(desktop_path, "output.txt")
with open(output_file, 'w') as f:
    for number, string in zip(numbers, strings):
        f.write(number + ',' + string + '\n')