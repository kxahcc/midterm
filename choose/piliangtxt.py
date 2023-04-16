import csv
import fileinput
nums = [0,2,4,6]
for num in nums:
    numst = str(num)
    dict = 'D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/camel-1.' + numst + '.csv'
    with open(dict, 'r') as csv_file, open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bug.txt',
                                           'w') as txt_file:
        csv_reader = csv.reader(csv_file)
        for row in csv_reader:
            txt_file.write(row[0] + '\n')

    with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bug.txt', 'r') as f_in, open(
            'D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bugdic.txt', 'w') as f_out:
        for line in f_in:
            new_line = line.replace('.', '/')
            f_out.write(new_line)

    dic = 'D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bugp' + numst + '.txt'
    with open('D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bugdic.txt', 'r') as f_in, open(dic,'w') as f_out:
        for line in f_in:
            new_line = 'D:/仓库/camel/1.'+numst+'/src/main/' + line
            f_out.write(new_line)
    with open(dic, 'r') as f:
        lines = f.readlines()
    with open(dic, 'w') as f:
        for line in lines:
            line = line.strip() + '.java'
            f.write(line + '\n')
        for line in fileinput.input("D:/仓库/PROMISE-backup-master/PROMISE-backup-master/bug-data/camel_csv/bugdic.txt", inplace=1):
         if not fileinput.isfirstline():
            print(line.replace('\n', ''))
    # 打开文件
    with open(dic, 'r') as f:
        # 读取文件内容
        lines = f.readlines()
    # 删除第一行
    lines.pop(0)
    # 再次打开文件，使用 'w' 模式写入修改后的内容
    with open(dic, 'w') as f:
        # 将修改后的内容写入文件
        f.writelines(lines)