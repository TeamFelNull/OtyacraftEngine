var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
var Opcodes = Java.type("org.objectweb.asm.Opcodes");
var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
function initializeCoreMod() {
  return {
    'oeitemoverlayintogui': {
      'target': {
        'type': 'CLASS',
        'name': 'net.minecraft.client.renderer.ItemRenderer'
      },
      'transformer': function (classNode) {
        var methods = classNode.methods;
        for (m in methods) {
          var method = methods[m];
          var num = 0;
          var name = method.name;
          if (name == "renderItemOverlayIntoGUI" || name == "func_180453_a") {
            ASMAPI.log('DEBUG', 'Hook ' + method.name + ":" + num);
            var i = method.instructions;
            var fi = i.get(num);
            i.insertBefore(fi, new VarInsnNode(Opcodes.ALOAD, 1));
            i.insertBefore(fi, new VarInsnNode(Opcodes.ALOAD, 2));
            i.insertBefore(fi, new VarInsnNode(Opcodes.ILOAD, 3));
            i.insertBefore(fi, new VarInsnNode(Opcodes.ILOAD, 4));
            i.insertBefore(fi, new VarInsnNode(Opcodes.ALOAD, 5));
            i.insertBefore(fi, new MethodInsnNode(Opcodes.INVOKESTATIC, "red/felnull/otyacraftengine/asm/hook/RenderHook", "renderItemOverlayIntoGUIHook", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", false));
            break;
          }
        }
        return classNode;
      }
    }
  }
}