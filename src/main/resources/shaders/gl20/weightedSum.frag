// $shader_type: fragment

#version 120

varying vec3 positionView;
varying vec3 normalView;
varying vec3 lightDirectionView;
varying vec4 positionClip;
varying vec4 previousPositionClip;

uniform vec4 modelColor;
uniform float diffuseIntensity;
uniform float specularIntensity;
uniform float ambientIntensity;

void main() {
    vec3 forwardNormalView = faceforward(normalView, lightDirectionView, normalView);

    float ambientTerm = ambientIntensity;

    float diffuseTerm;
    float normalDotLight = max(0, dot(forwardNormalView, -lightDirectionView));
    diffuseTerm = normalDotLight;

    float specularTerm;
    if (normalDotLight > 0) {
        specularTerm = pow(max(0, dot(reflect(-lightDirectionView, forwardNormalView), normalize(positionView))), 20);
    } else {
        specularTerm = 0;
    }

    vec4 color = modelColor;
    color.rgb *= (diffuseTerm + specularTerm + ambientTerm) * color.a;

    vec2 velocity = (positionClip.xy / positionClip.w - previousPositionClip.xy / previousPositionClip.w) * 0.5 * color.a;

    gl_FragData[0] = color;
    gl_FragData[1] = vec4(velocity, 0, 1);
    gl_FragData[2] = vec4(1, 1, 1, 1);
}
