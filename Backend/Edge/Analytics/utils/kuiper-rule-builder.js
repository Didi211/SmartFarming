export default class KuiperRuleBuilder { 
    static rule = { };

    static addId(id) { 
        this.rule.id = id;
        return this;
    }

    static addSqlString(stream, expression, triggerLevel) { 
        this.rule.sql = `SELECT 
        RULE_ID() AS ruleId,
        Humidity as reading,
        meta(tags) as tags,
        meta(deviceName) as deviceName
        FROM ${stream}
        WHERE Humidity ${expression} ${triggerLevel}`;
        return this;
    }

    static addAction(type, properties) {
        if (this.rule.actions == undefined) { 
            this.rule.actions = []
        }
        this.rule.actions.push({
            [type]: properties
        });
        return this;
    }

    static build() { 
        let rule = this.rule;
        this.rule = { }
        return rule;
    }
}