import android.os.Bundle;
import android.app.Activity;
${IMPORTS}

/**
 *
 * Created by Android Code Generator.
 *
 */
public class ${CLASS_NAME}Activity extends Activity ${INTERFACES} {

    ${FIELDS}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${RESOURCE_NAME});

        ${ASSIGNMENTS}
    }

    ${METHODS}
}
