package com.yss.rules.datavalidator.engine.rule;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule
import org.jeasy.rules.core.BasicRule;


/**
 * Builder to create {@link Rule} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class NewRuleBuilder extends BasicRule{

    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private int priority = DEFAULT_PRIORITY;

    private Condition condition = Condition.FALSE;
    private List<Action> actions = new ArrayList<>();

    NewRuleBuilder() {
    }

    NewRuleBuilder(String name, String description, int priority, Condition condition, List<Action> actions) {
        super(name, description, priority)
        this.condition = condition
        this.actions = actions
    }
/**
     * Set rule name.
     *
     * @param name of the rule
     * @return the builder instance
     */
    NewRuleBuilder 规则名(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return the builder instance
     */
    NewRuleBuilder 描述(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return the builder instance
     */
    NewRuleBuilder 优先级(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Set rule condition.
     *
     * @param condition of the rule
     * @return the builder instance
     */
    NewRuleBuilder 如果(Condition condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Add an action to the rule.
     *
     * @param action to add
     * @return the builder instance
     */
    NewRuleBuilder 那么(Action action) {
        this.actions.add(action);
        return this;
    }

    /**
     * Create a new {@link Rule}.
     *
     * @return a new rule instance
     */
    Rule 结束() {
        return new NewRuleBuilder(this.name,this.description,this.priority,condition,actions)
    }
    @Override
    boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }
    @Override
    void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}