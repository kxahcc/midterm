import os
# 设置要遍历的目录路径
dir_path = "/Users/wuxinrui/Desktop/ant_bug/1.5"
# 遍历目录下的所有Java文件
for dirpath, dirnames, filenames in os.walk(dir_path):
    for filename in filenames:
        if filename.endswith('.java'):
            # 获取Java文件路径和文件夹路径
            file_path = os.path.join(dirpath, filename)
            dir_name = os.path.dirname(file_path)
           
            # 调用joern-cli生成AST并保存为dot格式的文件
            os.system(f"./joern-parse {file_path}")
            file_name_without_ext = os.path.splitext(filename)[0]
            dotspath = dir_name+"/"+file_name_without_ext
            os.system(f"./joern-export --repr ast --out {dotspath}") 
