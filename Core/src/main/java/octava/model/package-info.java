@org.hibernate.annotations.GenericGenerators({
        @GenericGenerator(
                name = "BASE_GENERATOR",
                strategy = "enhanced-sequence",
                parameters = {
                        @org.hibernate.annotations.Parameter(
                                name = "sequence_name",
                                value = "BASE_SEQUENCE"
                        ),
                        @org.hibernate.annotations.Parameter(
                                name = "initial_value",
                                value = "3000"
                        )
                }
        )
})
package octava.model;

import org.hibernate.annotations.GenericGenerator;