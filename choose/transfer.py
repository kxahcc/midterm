import csv
with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/3.csv', 'r') as csv_file, open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/1.txt', 'w') as txt_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        txt_file.write(row[0] + '\n')
with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/1.txt', 'r') as f_in, open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/2.txt', 'w') as f_out:
    for line in f_in:
        new_line = line.replace('.', '/')
        f_out.write(new_line)
with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/2.txt', 'r') as f_in, open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/3.txt', 'w') as f_out:
    for line in f_in:
        new_line = 'src/main/' + line
        f_out.write(new_line)
with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/3.txt', 'r') as f:
    lines = f.readlines()
with open('../../仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/3.txt', 'w') as f:
    for line in lines:
        line = line.strip() + '.java'
        f.write(line + '\n')
import hashlib
# 读取文本文件中的每一行，获取文件路径
with open('../../仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/bugp3.txt', 'r') as f:
    file_paths = f.readlines()
# 遍历文件路径列表，将每个文件路径转换为MD5哈希值，并替换文本文件中的路径
with open('../../仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/ant_csv/bugp3.txt', 'w') as f:
    for file_path in file_paths:
        # 删除换行符和空格
        file_path = file_path.strip()
        # 计算文件路径的MD5哈希值
        md5_value = hashlib.md5(file_path.encode()).hexdigest()
        # 使用MD5哈希值作为新的文件路径
        new_path = file_path.replace(file_path, md5_value)
        # 将新的文件路径写入到新的文本文件中
        f.write(new_path + '\n')