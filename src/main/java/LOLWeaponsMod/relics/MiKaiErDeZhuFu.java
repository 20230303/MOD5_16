package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * "LOLWeaponsMod:MiKaiErDeZhuFu": {
 *     "NAME": "米凯尔的祝福",
 *     "FLAVOR": "圣泉之水涤净世间污浊，连诅咒也化作甘露。",
 *     "DESCRIPTIONS": ["回合开始时，获得一层随机能力"]
 *   },
 */
public class MiKaiErDeZhuFu extends CustomRelic {
    public static final String ID = ModHelper.makePath("MiKaiErDeZhuFu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/MiKaiErDeZhuFu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/MiKaiErDeZhuFu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.RARE;//类型：稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//音效：魔法
    public MiKaiErDeZhuFu() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new MiKaiErDeZhuFu();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atTurnStart() {
        flash();//闪烁
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));//在玩家头上出现

        // 存储所有可能的能力
        List<PowerFactory> powerFactories = new ArrayList<>();
        powerFactories.add(() -> new StrengthPower(AbstractDungeon.player, 1));//力量
        powerFactories.add(() -> new DexterityPower(AbstractDungeon.player, 1));//敏捷
        powerFactories.add(() -> new BlurPower(AbstractDungeon.player, 1));//残影（
        powerFactories.add(() -> new FocusPower(AbstractDungeon.player, 1));//集中
        powerFactories.add(() -> new VigorPower(AbstractDungeon.player, 1));//活力
        powerFactories.add(() -> new PlatedArmorPower(AbstractDungeon.player, 1));//多层护甲
        powerFactories.add(() -> new ArtifactPower(AbstractDungeon.player, 1));//人工制品
        powerFactories.add(() -> new MantraPower(AbstractDungeon.player, 1));//真言
        powerFactories.add(() -> new ThornsPower(AbstractDungeon.player, 1));//荆棘
        powerFactories.add(() -> new MetallicizePower(AbstractDungeon.player, 1));//金属化
        powerFactories.add(() -> new GainStrengthPower(AbstractDungeon.player, 1));//恶魔形态
        powerFactories.add(() -> new RitualPower(AbstractDungeon.player, 1, true));//仪式
        powerFactories.add(() -> new IntangiblePower(AbstractDungeon.player, 1));//无形
        powerFactories.add(() -> new RegenPower(AbstractDungeon.player, 1));//再生
        powerFactories.add(() -> new InvinciblePower(AbstractDungeon.player, 10));//坚不可摧
        powerFactories.add(() -> new EnergizedPower(AbstractDungeon.player, 1));//蓄能
        powerFactories.add(() -> new DrawPower(AbstractDungeon.player, 1));//下回合抽牌
        powerFactories.add(() -> new BurstPower(AbstractDungeon.player, 1));//爆发
        powerFactories.add(() -> new FreeAttackPower(AbstractDungeon.player, 1));//免费攻击
        powerFactories.add(() -> new DoubleDamagePower(AbstractDungeon.player, 1, true));//双倍伤害
        // 定义每个能力的权重
        int[] weights = {1,1,1,1,1,1,1,1,1}; // 可以根据需要调整权重

        // 计算累积权重
        int[] cumulativeWeights = new int[weights.length];
        cumulativeWeights[0] = weights[0];
        for (int i = 1; i < weights.length; i++) {
            cumulativeWeights[i] = cumulativeWeights[i - 1] + weights[i];
        }


        // 随机选择一个能力
        Random random = new Random();
        int randomValue = random.nextInt(cumulativeWeights[cumulativeWeights.length - 1]);
        int selectedIndex = 0;
        for (int i = 0; i < cumulativeWeights.length; i++) {
            if (randomValue < cumulativeWeights[i]) {
                selectedIndex = i;
                break;
            }
        }
        AbstractPower selectedPower = powerFactories.get(selectedIndex).createPower();

        // 应用选中的能力
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, selectedPower, 1));
    }

    // 定义一个函数式接口来创建能力
    @FunctionalInterface
    private interface PowerFactory {
        AbstractPower createPower();
    }
}
