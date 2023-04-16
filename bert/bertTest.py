import torch
from transformers import BertTokenizer, BertModel
from torch_geometric.data import Data

# 读取节点属性列表和边列表
with open('nodes.txt', 'r') as f:
    node_texts = f.readlines()

with open('edges.txt', 'r') as f:
    edges = f.readlines()

# 初始化 BERT tokenizer 和模型
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
model =  BertModel.from_pretrained('bert-base-uncased')

# 将节点属性列表转换为 BERT 输入
node_inputs = []
for i, text in enumerate(node_texts):
    encoded_text = tokenizer.encode(text.strip(), add_special_tokens=True)
    # 由于BERT模型的输入需要是固定长度的，因此需要将每个节点的输入进行padding，使其长度一致。
    # 这里将所有节点的输入padding到最长节点的长度。
    if i == 0:
        max_len = len(encoded_text)
    else:
        max_len = max(max_len, len(encoded_text))
    node_inputs.append(encoded_text)

for i, encoded_text in enumerate(node_inputs):
    node_inputs[i] = encoded_text + [0] * (max_len - len(encoded_text))

# 将边列表转换为 PyTorch Geometric 数据格式
src = []
dst = []
for edge in edges:
    u, v = edge.strip().split()
    src.append(int(u))
    dst.append(int(v))

g = Data(
    x=torch.tensor(node_inputs),
    edge_index=torch.tensor([src, dst]),
    edge_attr=None
)

outputs = model(g.x, attention_mask=(g.x != 0).long(), output_hidden_states=True)

# 将 BERT 输出用于 GCN 模型训练
last_hidden_state = outputs.hidden_states[-1]
g.x = last_hidden_state
print(g.x)
# 这里可以进一步添加 GCN 模型的代码以进行训练


