package com.example.fitbox;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;

import java.nio.FloatBuffer;
import java.util.Map;
import java.util.HashMap;

public class Recognition {
    // Make predictions with given inputs
    static String runPrediction(float[] features, OrtSession ortSession, OrtEnvironment ortEnvironment) {
        try {
            // Get the name of the input node
            String inputName = ortSession.getInputNames().iterator().next();

            // Make a FloatBuffer of the inputs
            FloatBuffer floatBufferInputs = FloatBuffer.wrap(features);

            // Create input tensor with floatBufferInputs of shape (1, 40)
            long[] featuresShape = {1, 40};
            OnnxTensor inputTensor = OnnxTensor.createTensor(ortEnvironment, floatBufferInputs, featuresShape);

            // Run the model
            Map<String, OnnxTensor> inputMap = new HashMap<>();
            inputMap.put(inputName, inputTensor);
            OrtSession.Result results = ortSession.run(inputMap);

            // Fetch and return the first element from the string array
            String[] output = (String[]) results.get(0).getValue();
            return output[0];
        } catch (OrtException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return "Error"; // Or return an error message
        }
    }


}

