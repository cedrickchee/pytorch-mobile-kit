import torch
import torchvision

model = torchvision.models.resnext50_32x4d(pretrained=True)
model.eval()
input = torch.rand(1, 3, 224, 224)
traced_script_module = torch.jit.trace(model, input)
traced_script_module.save("../PyTorchMobileKit/app/src/main/assets/resnext50_32x4d.pt")
