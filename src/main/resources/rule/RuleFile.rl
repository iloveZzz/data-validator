规则引擎 {
            添加规则 {
                规则名 'test1'
                描述 '测试一个规则1'
                优先级 1
                如果 { facts -> 是否成年 }
                那么 { facts ->
                    System.out.println("test 1 rule!")
                }
                结束()
            }
            添加规则 {
                规则名 'test2'
                描述 '测试一个规则2'
                优先级 2
                如果 { facts -> 姓名！=null&&年龄!=null }
                那么 { facts ->
                    System.out.println("test 2 rule!")
                }
                结束()
            }
            添加规则 {
                规则名 'test3'
                描述 '测试一个规则3'
                优先级 3
                如果 { facts -> true }
                那么 { facts ->
                    System.out.println("test 3 rule!")
                }
                结束()
            }
}