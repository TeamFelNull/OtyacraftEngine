package red.felnull.otyacraftengine.block;

/*
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestBlock extends Block {
    public static final Block TEST = new TestBlock(AbstractBlock.Properties.create(Material.ANVIL).hardnessAndResistance(1f, 1f)).setRegistryName(new ResourceLocation(OtyacraftEngine.MODID, "test_block"));
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape PART_LOWER_X = Block.makeCuboidShape(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape PART_MID_X = Block.makeCuboidShape(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape PART_UPPER_X = Block.makeCuboidShape(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape PART_LOWER_Z = Block.makeCuboidShape(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    private static final VoxelShape PART_MID_Z = Block.makeCuboidShape(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    private static final VoxelShape PART_UPPER_Z = Block.makeCuboidShape(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape X_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_X, PART_MID_X, PART_UPPER_X);
    private static final VoxelShape Z_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_Z, PART_MID_Z, PART_UPPER_Z);

    public TestBlock(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onBlockRegistry(final RegistryEvent.Register<Block> e) {
        e.getRegistry().register(TEST);

    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new BlockItem(TEST, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(OtyacraftEngine.MODID, "test_block"));
    }

    @SubscribeEvent
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape test = X_AXIS_AABB;
        return IKSGVoxelShapeUtil.rotate90(test);
    }
}*/
