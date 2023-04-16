import os
import hashlib
import shutil
# 原始文件夹路径
orig_folder = r"D:\仓库\PROMISE-backup-master\PROMISE-backup-master\bug-data\camel_csv"
# 新建文件夹路径
new_folder = r"D:\sourcecode\camel\6.0"
# 获取原始文件夹中的文件名列表
files = os.listdir(orig_folder)
# 遍历文件列表，对每个文件进行处理
for file in files:
    # 获取文件路径
    file_path = os.path.join(orig_folder, file)
    # 如果不是txt文件，则跳过
    if not file.endswith(".txt"):
        continue
    # 读取文件中的路径
    with open(file_path, "r") as f:
        paths = f.read().splitlines()
    # 对路径进行MD5加密并创建新文件夹
    for path in paths:
        # 判断源文件是否存在
        if not os.path.isfile(os.path.join(orig_folder, path)):
            continue
        md5 = hashlib.md5(path.encode()).hexdigest()
        new_folder_path = os.path.join(new_folder, md5)
        if not os.path.exists(new_folder_path):
            os.makedirs(new_folder_path)
        # 将地址后面的文件从原始文件夹中复制到新文件夹中
        filename = os.path.basename(path)
        old_filepath = os.path.join(orig_folder, path)
        new_filepath = os.path.join(new_folder_path, filename)
        shutil.copy(old_filepath, new_filepath)