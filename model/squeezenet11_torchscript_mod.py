import torch
import torchvision

model = torchvision.models.squeezenet1_1(pretrained=True)
model.eval()
input = torch.rand(1, 3, 224, 224)
traced_script_module = torch.jit.trace(model, input)
traced_script_module.save("../PyTorchMobileKit/app/src/main/assets/squeezenet1_1.pt")
