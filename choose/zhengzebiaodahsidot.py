import re
import os
# 获取桌面路径
desktop_path = os.path.join(os.path.expanduser('~'), 'Desktop')
# 读取文件内容
with open(os.path.join(desktop_path, '0-ast.dot'), 'r', encoding='utf-8') as f:
    content = f.read()
# 将&lt;转化为<，将&gt;转化为>，将&quot;转化为"
content = re.sub('&lt;', '<', content)
content = re.sub('&gt;', '>', content)
content = re.sub('&quot;', '"', content)
# 使用正则表达式提取[]内的内容
pattern = r'\[(.*?)\]'
result = re.findall(pattern, content)
# 使用正则表达式去掉<SUB>后面的内容
pattern = r'<SUB>.*'
result = [re.sub(pattern, '', r) for r in result]
# 使用正则表达式去掉所有非字母和数字的符号，除了负号，并且每个字符串间隔用|分开
pattern = r'[^a-zA-Z0-9-]+'
result = ['|'.join(re.findall(r'[a-zA-Z0-9-]+', r)) for r in result]
result = [r.replace('label|', '') for r in result]
# 将提取出来的内容写入文件
with open(os.path.join(desktop_path, 'result.txt'), 'w', encoding='utf-8') as f:
    for r in result:
        f.write(r + '\n')