import os
import pandas as pd
# 指定原始csv文件所在的文件夹路径
folder_path = 'D:\仓库\PROMISE-backup-master\PROMISE-backup-master\\bug-data\\camel'
# 指定新csv文件存放的文件夹路径
new_folder_path = 'D:\仓库\PROMISE-backup-master\PROMISE-backup-master\\bug-data\\camel_csv'
# 如果新文件夹不存在，则创建新文件夹
if not os.path.exists(new_folder_path):
    os.makedirs(new_folder_path)
# 遍历文件夹中的所有csv文件
for file_name in os.listdir(folder_path):
    if file_name.endswith('.csv'):
        # 读取csv文件
        file_path = os.path.join(folder_path, file_name)
        df = pd.read_csv(file_path)
        # 筛选出最后一列为0的行
        df = df[df.iloc[:, -1] == 0]
        # 将筛选结果写入新的csv文件
        new_file_path = os.path.join(new_folder_path, file_name)
        df.to_csv(new_file_path, index=False)