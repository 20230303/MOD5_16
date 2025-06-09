package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**遗物：荆棘之甲
 * 效果：每回合开始，获得2点荆棘。
 * */
public class JingJiZhiJia extends CustomRelic {
    public static final String ID = ModHelper.makePath("JingJiZhiJia");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/JingJiZhiJia.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/JingJiZhiJia.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：罕见
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;//声音：
    public JingJiZhiJia() {//注意修改这里 TODO
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new JingJiZhiJia();//修改这里 TODO
    }
    // 遗物效果，可在此处添加代码 TODO
    @Override
    public void atTurnStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 2), 2));
    }

}
