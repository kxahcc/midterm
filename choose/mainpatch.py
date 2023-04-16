import pandas as pd
import os
# 获取文件夹中的csv文件列表
folder_path = r'D:\仓库\PROMISE-backup-master\PROMISE-backup-master\bug-data\ant'
csv_files = [os.path.join(folder_path, f) for f in os.listdir(folder_path) if f.endswith('.csv')]
# 读取第一个csv文件
prev_df = pd.read_csv(csv_files[0])
# 遍历剩余csv文件
for i in range(1, len(csv_files)):
    curr_df = pd.read_csv(csv_files[i])
    result_df = pd.DataFrame()
    # 遍历当前csv文件的每一行
    for index, row in curr_df.iterrows():
        if row.iloc[0] in prev_df.iloc[:, 0].values:
            prev_row = prev_df.loc[prev_df.iloc[:, 0] == row.iloc[0]].iloc[-1]
            if row.iloc[-1] < prev_row.iloc[-1]:
                result_df = pd.concat([result_df, row], axis=0)
    # 如果result_df不为空，将其写入新的csv文件
    if not result_df.empty:
        result_df.to_csv(os.path.join(folder_path, f"new_{i}.csv"), index=False)
    # 将当前csv文件赋值为前一个csv文件，继续循环
    prev_df = curr_df